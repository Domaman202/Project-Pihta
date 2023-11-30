package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.INUP
import ru.DmN.pht.std.ast.NodeLazySymbol
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.processors.IStdNodeProcessor
import ru.DmN.pht.std.processors.NRSymbol

object NUPLazySymbol : INUP<NodeLazySymbol, NodeLazySymbol>, IStdNodeProcessor<NodeLazySymbol> {
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