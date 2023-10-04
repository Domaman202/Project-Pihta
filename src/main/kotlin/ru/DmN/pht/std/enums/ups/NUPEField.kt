package ru.DmN.pht.std.enums.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.VirtualField
import ru.DmN.pht.std.base.processor.ctx.EnumConstContext
import ru.DmN.pht.std.base.processor.utils.clazz
import ru.DmN.pht.std.base.processor.utils.enum
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor
import ru.DmN.pht.std.base.utils.computeList
import ru.DmN.pht.std.base.utils.computeString
import ru.DmN.pht.std.enums.ast.NodeEField

object NUPEField : INodeUniversalProcessor<NodeEField, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx, operationToken)

    override fun unparse(node: NodeEField, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ').append('[')
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

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeEField {
        val type = ctx.clazz
        val fields = type.fields
        val enums = ctx.enum.enums
        val list = ArrayList<Pair<String, List<Node>>>()
        processor.computeList(node.nodes[0], ctx).map { processor.computeList(it, ctx) }.forEach {
            val name = processor.computeString(it[0], ctx)
            val nodes = it.drop(1)
            list.add(Pair(name, nodes))
            fields += VirtualField(type, name, type, static = false, enum = true)
            enums += EnumConstContext(name, nodes)
        }
        return NodeEField(node.tkOperation, list)
    }
}