package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.siberia.utils.INUP
import ru.DmN.pht.std.processors.IStdNodeProcessor
import ru.DmN.pht.std.utils.VTWG
import ru.DmN.siberia.lexer.Token.DefaultType.*

object NUPValueA : INUP<NodeValue, NodeValue>, IStdNodeProcessor<NodeValue> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): NodeValue {
        val value = parser.nextToken()!!
        return NodeValue(
            token, when (value.type) {
                OPERATION -> if (value.text == "nil") NodeValue.Type.NIL else throw RuntimeException()
                CLASS   -> NodeValue.Type.CLASS
                NAMING  -> NodeValue.Type.NAMING
                STRING  -> NodeValue.Type.STRING
                NIL     -> NodeValue.Type.NIL
                BOOLEAN -> NodeValue.Type.BOOLEAN
                INTEGER -> NodeValue.Type.INT
                LONG    -> NodeValue.Type.LONG
                FLOAT   -> NodeValue.Type.FLOAT
                DOUBLE  -> NodeValue.Type.DOUBLE
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
                NodeValue.Type.CLASS,
                NodeValue.Type.CLASS_WITH_GEN -> unparseType(node.value)
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
                NodeValue.Type.NIL    -> "Any"
                NodeValue.Type.BOOLEAN-> "boolean"
                NodeValue.Type.CHAR   -> "char"
                NodeValue.Type.INT    -> "int"
                NodeValue.Type.LONG   -> "long"
                NodeValue.Type.FLOAT  -> "float"
                NodeValue.Type.DOUBLE -> "double"
                NodeValue.Type.STRING,
                NodeValue.Type.NAMING -> "String"
                NodeValue.Type.PRIMITIVE,
                NodeValue.Type.CLASS,
                NodeValue.Type.CLASS_WITH_GEN -> "Class"
            }, processor.tp
        )

    override fun process(node: NodeValue, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        if (node.vtype == NodeValue.Type.CLASS)
            NodeValue(node.token, NodeValue.Type.CLASS, computeType(node, processor, ctx).name)
        else node

    override fun computeInt(node: NodeValue, processor: Processor, ctx: ProcessingContext): Int =
        node.getInt()

    override fun computeString(node: NodeValue, processor: Processor, ctx: ProcessingContext): String =
        node.getString()

    override fun computeType(node: NodeValue, processor: Processor, ctx: ProcessingContext): VirtualType {
        val gs = node.value.indexOf('<')
        if (gs == -1)
            return ctx.global.getType(node.value, processor.tp)
        val gctx = ctx.global
        val generics = ArrayList<VirtualType>()
        var s = node.value.substring(gs)
        while (true) {
            val i = s.indexOf(',')
            generics.add(gctx.getType(s.substring(2, if (i == -1) s.length - 1 else i), processor.tp))
            if (i == -1)
                break
            s = s.substring(i + 1)
        }
        return VTWG(gctx.getType(node.value.substring(0, gs), processor.tp), generics)
    }

    override fun computeGenericType(node: NodeValue, processor: Processor, ctx: ProcessingContext): String? =
        if (node.value.endsWith('^'))
            node.value.substring(0, node.value.length - 1)
        else null
}