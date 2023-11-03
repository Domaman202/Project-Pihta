package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.ast.NodeIncDec
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.unparsers.NUDefaultX

object NUPIncDec : INodeUniversalProcessor<NodeIncDec, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(
            parser, ctx, Token.operation(
                operationToken.line,
                when (operationToken.text!!) {
                    "++" -> "inc"
                    "--" -> "dec"
                    else -> operationToken.text
                }
            )
        )

    override fun unparse(node: NodeIncDec, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append('(').append(NUDefaultX.text(node.token)).append(' ').append(node.name).append(')')
    }
}