package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ctx.ParsingContext
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeIncDec
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.processors.NRMath
import ru.DmN.pht.std.unparsers.NUDefaultX

object NUPIncDec : INodeUniversalProcessor<NodeIncDec, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        NPDefault.parse(parser, ctx, token)

    override fun unparse(node: NodeIncDec, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.append('(').append(NUDefaultX.text(node.token)).append(' ').append(node.name).append(')')
    }

    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRMath.findExtend(processor.calc(node.nodes[0], ctx)!!, NUDefaultX.text(node.token), node.nodes.drop(1), processor, ctx)?.rettype
}