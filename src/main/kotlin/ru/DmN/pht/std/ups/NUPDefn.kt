package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.pht.std.ast.NodeDefn
import ru.DmN.pht.std.processors.INodeUniversalProcessor

object NUPDefn : INodeUniversalProcessor<NodeDefn, NodeDefn> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        NPDefault.parse(parser, ctx, token)

    override fun unparse(node: NodeDefn, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            node.method.apply {
                append('(').append(node.token.text)
                    .append(' ').append(name)
                    .append(" ^").append(rettype.name)
                    .append(" [")
                argsn.forEachIndexed { i, it ->
                    append('[').append(it).append(' ').append(NUPValueA.unparseType(argsc[i].name)).append(']')
                    if (i + 1 < argsn.size) {
                        append(' ')
                    }
                }
                append(']')
                if (node.nodes.isNotEmpty()) {
                    append('\n')
                    node.nodes.forEachIndexed { i, it ->
                        append("\t".repeat(indent + 1))
                        unparser.unparse(it, ctx, indent + 1)
                        if (i + 1  < node.nodes.size) {
                            append('\n')
                        }
                    }
                }
                append(')')
            }
        }
    }
}