package ru.DmN.pht.std.macro.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.processors.NRDefault
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.base.processor.utils.nodePrognOf
import ru.DmN.pht.std.base.processor.utils.sliceInsert
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor
import ru.DmN.pht.std.base.utils.computeList
import ru.DmN.pht.std.base.utils.computeString
import ru.DmN.pht.std.macro.ast.NodeMacroArg
import ru.DmN.pht.std.macro.ast.NodeMacroInline

object NUPMacroInline : INodeUniversalProcessor<NodeMacroInline, NodeMacroInline> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node =
        NPDefault.parse(parser, ctx) {
            NodeMacroInline(operationToken, it.toMutableList(), ctx.macros.reversed())
        }

    override fun unparse(node: NodeMacroInline, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        throw UnsupportedOperationException("Not yet implemented")

    override fun process(node: NodeMacroInline, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val names = processor.computeList(node.nodes[0], ctx).map { processor.computeString(it, ctx) }
        node.nodes.drop(1).forEach { expr ->
            for (i in 0 until expr.nodes.size) {
                val it = expr.nodes[i]
                if (it is NodeMacroArg && names.any { name -> processor.computeString(it.nodes[0], ctx) == name }) {
                    sliceInsert(expr.nodes as MutableList<Any?>, i, processor.process(it, ctx, mode)!!.nodes)
                }
            }
        }
        return NRDefault.process(
            nodePrognOf(node.tkOperation.line, node.nodes.drop(1).toMutableList()),
            processor,
            ctx,
            mode
        )
    }
}