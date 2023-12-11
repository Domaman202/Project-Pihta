package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.INUP
import ru.DmN.pht.std.processors.NRMath
import ru.DmN.pht.std.unparsers.NUDefaultX
import ru.DmN.siberia.ups.NUPDefault

object NUPIncDecA : INUP<NodeNodesList, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        NPDefault.parse(parser, ctx, token)

    override fun unparse(node: NodeNodesList, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        NUPDefault.unparse(node, unparser, ctx, indent)
    }

    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRMath.findExtend(processor.calc(node.nodes[0], ctx)!!, NUDefaultX.text(node.token), node.nodes.drop(1), processor, ctx)?.rettype
}