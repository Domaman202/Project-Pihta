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
import ru.DmN.pht.std.math.ast.NodeMath
import ru.DmN.pht.std.math.ast.NodeMath.Operation.*

object NUPMath : INodeUniversalProcessor<NodeMath, NodeMath> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node =
        NPDefault.parse(parser, ctx) {
            NodeMath(
                operationToken, when (operationToken.text!!) {
                    "+" -> ADD
                    "-" -> SUB
                    "*" -> MUL
                    "/" -> DIV
                    else -> throw RuntimeException()
                }, it
            )
        }

    override fun unparse(node: NodeMath, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.run {
            append('(').append(
                when (node.operation) {
                    ADD -> "+"
                    SUB -> "-"
                    MUL -> "*"
                    DIV -> "/"
                }
            ).append(' ')
            NUDefault.unparseNodes(node, unparser, ctx, indent)
            append(')')
        }
    }
}