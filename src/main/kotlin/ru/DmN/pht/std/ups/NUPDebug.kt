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
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparsers.NUDefault
import ru.DmN.pht.std.processors.INodeUniversalProcessor

object NUPDebug : INodeUniversalProcessor<NodeNodesList, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx, operationToken)

    override fun unparse(node: NodeNodesList, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        NUDefault.unparse(node, unparser, ctx, indent)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNodesList {
        var i = 0
        while (i < node.nodes.size) {
            val it = processor.process(node.nodes[i], ctx, ValType.VALUE)
            if (it == null) {
                node.nodes.removeAt(i)
                i--
            } else node.nodes[i] = it
            i++
        }
        return node
    }
}