package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ctx.ParsingContext
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.ast.NodeFieldB
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.unparsers.NUDefaultX

object NUPFieldB : INodeUniversalProcessor<NodeFieldB, NodeFieldB> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        NPDefault.parse(parser, ctx, token)

    override fun unparse(node: NodeFieldB, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(NUDefaultX.text(node.token)).append(' ').append('[')
            node.fields.forEach {
                append('[').append(it.name).append(" ^").append(it.type.name).append(']')
            }
            append("])")
        }
    }
}