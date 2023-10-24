package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeMath
import ru.DmN.pht.std.ast.NodeMath.Operation.*
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.unparsers.NUDefaultX

object NUPMath : INodeUniversalProcessor<NodeMath, NodeMath> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node =
        NPDefault.parse(parser, ctx) {
            NodeMath(
                operationToken,
                it,
                when (operationToken.text!!) {
                    "+" -> PLUS
                    "-" -> MINUS
                    "*" -> MUL
                    "/" -> DIV
                    "%" -> REM
                    else -> throw RuntimeException()
                }
            )
        }

    override fun unparse(node: NodeMath, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        NUDefaultX.unparse(node, unparser, ctx, indent)

    override fun calc(node: NodeMath, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes[0], ctx)
}