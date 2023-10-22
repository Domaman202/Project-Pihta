package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.ast.NodeLazySymbol
import ru.DmN.pht.std.processors.IStdNodeProcessor
import ru.DmN.pht.std.processors.NRSymbol

object NUPLazySymbol : INodeUniversalProcessor<NodeLazySymbol, NodeLazySymbol>, IStdNodeProcessor<NodeLazySymbol> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node =
        NPDefault.parse(parser, ctx) { NodeLazySymbol(operationToken, it, null) }

    override fun unparse(node: NodeLazySymbol, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        throw UnsupportedOperationException("Not yet implemented")

    override fun process(node: NodeLazySymbol, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        null

    override fun computeString(node: NodeLazySymbol, processor: Processor, ctx: ProcessingContext): String =
        if (node.symbol == null)
            NRSymbol.computeString(node, processor, ctx).apply { node.symbol = this }
        else node.symbol!!
}