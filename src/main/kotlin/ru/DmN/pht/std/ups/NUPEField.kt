package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.VirtualField.VirtualFieldImpl
import ru.DmN.siberia.utils.VirtualType.VirtualTypeImpl
import ru.DmN.pht.std.ast.NodeEField
import ru.DmN.pht.std.processor.ctx.EnumConstContext
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.enum
import ru.DmN.siberia.utils.INUP
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString

object NUPEField : INUP<NodeEField, NodeNodesList> {
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
        val type = ctx.clazz as VirtualTypeImpl
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