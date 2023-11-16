package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.VirtualField.VirtualFieldImpl
import ru.DmN.pht.std.ast.NodeEField
import ru.DmN.pht.std.processor.ctx.EnumConstContext
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.enum
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString

object NUPEField : INodeUniversalProcessor<NodeEField, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        NPDefault.parse(parser, ctx, token)

    override fun unparse(node: NodeEField, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.token.text).append(' ').append('[')
            node.fields.forEach {
                append('\n').append("\t".repeat(indent + 1)).append('[').append(it.first)
                if (it.second.isNotEmpty()) {
                    append('\n')
                    it.second.forEachIndexed { i, n ->
                        append("\t".repeat(indent + 2))
                        unparser.unparse(n, ctx, indent + 1)
                        if (i + 1 < it.second.size) {
                            append('\n')
                        }
                    }
                }
                append(']')
            }
            append("])")
        }
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeEField? {
        val type = ctx.clazz
        val fields = type.fields
        val enums = ctx.enum.enums
        val list = ArrayList<Pair<String, List<Node>>>()
        processor.computeList(node.nodes[0], ctx).map { processor.computeList(it, ctx) }.forEach {
            val name = processor.computeString(it[0], ctx)
            val nodes = it.drop(1)
            list.add(Pair(name, nodes))
            fields += VirtualFieldImpl(type, name, type, isStatic = true, isEnum = true)
            enums += EnumConstContext(name, nodes)
        }
        return NodeEField(node.token, list)
    }
}