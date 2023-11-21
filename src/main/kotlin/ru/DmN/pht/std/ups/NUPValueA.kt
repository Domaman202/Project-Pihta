package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ctx.ParsingContext
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.processors.IStdNodeProcessor

object NUPValueA : INodeUniversalProcessor<NodeValue, NodeValue>, IStdNodeProcessor<NodeValue> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): NodeValue {
        val value = parser.nextToken()!!
        return NodeValue(
            token, when (value.type) {
                Token.Type.OPERATION -> if (value.text == "nil") NodeValue.Type.NIL else throw RuntimeException()
                Token.Type.CLASS -> NodeValue.Type.CLASS
                Token.Type.NAMING -> NodeValue.Type.NAMING
                Token.Type.STRING -> NodeValue.Type.STRING
                Token.Type.NIL -> NodeValue.Type.NIL
                Token.Type.BOOLEAN -> NodeValue.Type.BOOLEAN
                Token.Type.INTEGER -> NodeValue.Type.INT
                Token.Type.LONG -> NodeValue.Type.LONG
                Token.Type.FLOAT -> NodeValue.Type.FLOAT
                Token.Type.DOUBLE -> NodeValue.Type.DOUBLE
                else -> throw RuntimeException()
            }, value.text!!
        )
    }

    override fun unparse(node: NodeValue, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append(
            when (node.vtype) {
                NodeValue.Type.NIL -> "nil"
                NodeValue.Type.BOOLEAN,
                NodeValue.Type.CHAR,
                NodeValue.Type.INT,
                NodeValue.Type.LONG,
                NodeValue.Type.FLOAT,
                NodeValue.Type.DOUBLE -> node.value
                NodeValue.Type.STRING -> "\"${node.value.replace("\"", "\\\"").replace("\n", "\\n").replace("\t", "\\t")}\""
                NodeValue.Type.PRIMITIVE,
                NodeValue.Type.CLASS -> unparseType(node.value)
                NodeValue.Type.NAMING -> "#${node.value}"
            }
        )
    }

    fun unparseType(type: String): String =
        if (type.startsWith("["))
            "(array-type ${unparseType(type.substring(1))})"
        else "^$type"

    override fun calc(node: NodeValue, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType(
            when (node.vtype) {
                NodeValue.Type.NIL -> "Any"
                NodeValue.Type.BOOLEAN -> "boolean"
                NodeValue.Type.CHAR -> "char"
                NodeValue.Type.INT -> "int"
                NodeValue.Type.LONG -> "long"
                NodeValue.Type.FLOAT -> "float"
                NodeValue.Type.DOUBLE -> "double"
                NodeValue.Type.STRING, NodeValue.Type.NAMING -> "String"
                NodeValue.Type.PRIMITIVE, NodeValue.Type.CLASS -> "Class"
            }, processor.tp
        )

    override fun process(node: NodeValue, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (node.vtype == NodeValue.Type.CLASS)
            NodeValue(node.token, NodeValue.Type.CLASS, ctx.global.getType(node.value, processor.tp).name)
        else node

    override fun computeString(node: NodeValue, processor: Processor, ctx: ProcessingContext): String =
        node.getString()

    override fun computeInt(node: NodeValue, processor: Processor, ctx: ProcessingContext): Int =
        node.getInt()
}