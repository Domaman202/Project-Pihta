package ru.DmN.pht.std.ups

import ru.DmN.pht.std.ast.NodeGensNodesList
import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.NRDefault
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.INUP
import ru.DmN.siberia.utils.VirtualType

object NUPGensDefault : INUP<NodeGensNodesList, NodeGensNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NPDefault.parse(parser, ctx) { NodeGensNodesList(token, it, emptyList()) }

    override fun unparse(node: NodeGensNodesList, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        NUDefault.unparse(node, unparser, ctx, indent)

    override fun calc(node: NodeGensNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRDefault.calc(node, processor, ctx)

    override fun process(node: NodeGensNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        NRDefault.process(node, processor, ctx, mode)
}