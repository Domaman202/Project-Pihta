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
import ru.DmN.pht.base.processors.NRDefault
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.processors.IStdNodeUniversalProcessor
import ru.DmN.pht.std.unparsers.NUDefaultX

object NUPDefaultX : IStdNodeUniversalProcessor<NodeNodesList, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        NPDefault.parse(parser, ctx, token)

    override fun unparse(node: NodeNodesList, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        NUDefaultX.unparse(node, unparser, ctx, indent)

    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRDefault.calc(node, processor, ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        NRDefault.process(node, processor, ctx, mode)

    override fun computeList(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): List<Node> =
        node.nodes
}