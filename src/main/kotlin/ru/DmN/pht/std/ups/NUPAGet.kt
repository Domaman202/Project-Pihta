package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ctx.ParsingContext
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.ast.NodeAGet
import ru.DmN.pht.std.unparsers.NUDefaultX

object NUPAGet : INodeUniversalProcessor<NodeAGet, NodeAGet> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        NPDefault.parse(parser, ctx, token)

    override fun unparse(node: NodeAGet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(NUDefaultX.text(node.token)).append('\n').append("\t".repeat(indent + 1))
            unparser.unparse(node.arr, ctx, indent + 1)
            append('\n').append("\t".repeat(indent + 1))
            unparser.unparse(node.index, ctx, indent + 1)
            append(')')
        }
    }

    override fun calc(node: NodeAGet, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.arr, ctx)!!.componentType
}