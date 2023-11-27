package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.pht.std.ast.NodeFieldB
import ru.DmN.siberia.utils.INUP
import ru.DmN.pht.std.unparsers.NUDefaultX

object NUPFieldB : INUP<NodeFieldB, NodeFieldB> {
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