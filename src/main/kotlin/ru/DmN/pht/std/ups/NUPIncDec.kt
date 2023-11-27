package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.std.ast.NodeIncDec
import ru.DmN.siberia.utils.INUP
import ru.DmN.pht.std.processors.NRMath
import ru.DmN.pht.std.unparsers.NUDefaultX

object NUPIncDec : INUP<NodeIncDec, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        NPDefault.parse(parser, ctx, token)

    override fun unparse(node: NodeIncDec, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append('(').append(NUDefaultX.text(node.token)).append(' ').append(node.name).append(')')
    }

    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRMath.findExtend(processor.calc(node.nodes[0], ctx)!!, NUDefaultX.text(node.token), node.nodes.drop(1), processor, ctx)?.rettype
}