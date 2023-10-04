package ru.DmN.pht.std.math.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparser.unparsers.NUDefault
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor
import ru.DmN.pht.std.math.ast.NodeEquals

object NUPEqual : INodeUniversalProcessor<NodeEquals, NodeEquals> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx) {
            NodeEquals(
                operationToken, when (operationToken.text!!) {
                    "=" -> NodeEquals.Operation.EQ
                    "!=" -> NodeEquals.Operation.NE
                    "<" -> NodeEquals.Operation.LT
                    "<=" -> NodeEquals.Operation.LE
                    ">" -> NodeEquals.Operation.GT
                    ">=" -> NodeEquals.Operation.GE
                    else -> throw RuntimeException()
                }, it
            )
        }

    override fun unparse(node: NodeEquals, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(when (node.operation) {
                NodeEquals.Operation.EQ -> "="
                NodeEquals.Operation.NE -> "!="
                NodeEquals.Operation.LT -> "<"
                NodeEquals.Operation.LE -> "<"
                NodeEquals.Operation.GT -> "<"
                NodeEquals.Operation.GE -> "<"
            }).append(' ')
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}