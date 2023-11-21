package ru.DmN.pht.base.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.unparsers.NUDefault
import ru.DmN.pht.std.processor.utils.exports
import ru.DmN.pht.std.processors.INodeUniversalProcessor

object NUPExport : INodeUniversalProcessor<NodeNodesList, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx, operationToken).apply { ctx.exports.peek() += this as NodeNodesList }

    override fun unparse(node: NodeNodesList, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        NUDefault.unparse(node, unparser, ctx, indent)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        null
}