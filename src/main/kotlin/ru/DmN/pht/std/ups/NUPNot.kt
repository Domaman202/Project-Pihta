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
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparsers.NUDefault
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.processor.utils.nodeMCall
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.processors.NRMCall
import ru.DmN.pht.std.utils.line

object NUPNot : INodeUniversalProcessor<NodeNodesList, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx, Token.operation(operationToken.line, "not"))

    override fun unparse(node: NodeNodesList, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        NUDefault.unparse(node, unparser, ctx, indent)

    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? {
        val type = processor.calc(node.nodes[0], ctx)
        return if (type!!.isPrimitive)
            type
        else NRMCall.calc(nodeMCall(node.token.line, node.nodes[0], "not", emptyList()), processor, ctx)
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        val value = processor.process(node.nodes[0], ctx, ValType.VALUE)!!
        return if (processor.calc(value, ctx)!!.isPrimitive)
            if (mode == ValType.VALUE)
                NodeNodesList(node.token.processed(), mutableListOf(value))
            else null
        else NRMCall.process(
            nodeMCall(node.line, value, "not", emptyList()),
            processor,
            ctx,
            ValType.VALUE
        )
    }
}