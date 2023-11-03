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
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.processor.utils.nodeMCall
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.processors.NRMCall
import ru.DmN.pht.std.unparsers.NUDefaultX

object NUPMath : INodeUniversalProcessor<NodeNodesList, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node =
        NPDefault.parse(parser, ctx) {
            val text = operationToken.text!!
            NodeNodesList(
                Token.operation(
                    operationToken.line,
                    when (text) {
                        "+" -> "add"
                        "-" -> "sub"
                        "*" -> "mul"
                        "/" -> "div"
                        "%" -> "rem"
                        else -> text
                    }
                ),
                it
            )
        }

    override fun unparse(node: NodeNodesList, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        NUDefaultX.unparse(node, unparser, ctx, indent)

    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType {
        val firstType = processor.calc(node.nodes[0], ctx)
        return if (firstType!!.isPrimitive)
            firstType // todo: primitive extensions
        else NRMCall.calc(
            nodeMCall(
                node.token.line,
                node.nodes[0],
                node.token.text!!,
                node.nodes.drop(1)
            ), processor, ctx
        )
    }
}