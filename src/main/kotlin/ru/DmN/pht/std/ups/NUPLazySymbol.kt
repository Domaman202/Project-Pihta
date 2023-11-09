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
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.processors.IStdNodeProcessor
import ru.DmN.pht.std.processors.NRSymbol

object NUPLazySymbol : INodeUniversalProcessor<NodeLazySymbol, NodeLazySymbol>, IStdNodeProcessor<NodeLazySymbol> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NPDefault.parse(parser, ctx) { NodeLazySymbol(token, it, null) }

    override fun unparse(node: NodeLazySymbol, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        TODO("Not yet implemented")

    override fun process(node: NodeLazySymbol, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == ValType.VALUE)
            NodeValue.of(node.token.line, NodeValue.Type.STRING, computeString(node, processor, ctx))
        else null

    override fun computeString(node: NodeLazySymbol, processor: Processor, ctx: ProcessingContext): String =
        if (node.symbol == null)
            NRSymbol.computeString(node, processor, ctx).apply { node.symbol = this }
        else node.symbol!!
}