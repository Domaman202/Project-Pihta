package ru.DmN.pht.std.ups

import ru.DmN.pht.std.ast.NodeMacroArg
import ru.DmN.pht.std.ast.NodeMacroInline
import ru.DmN.pht.std.parser.utils.macros
import ru.DmN.pht.std.processor.utils.sliceInsert
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.nodeProgn
import ru.DmN.siberia.processors.NRDefault
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.INUP

object NUPMacroInline : INUP<NodeMacroInline, NodeMacroInline> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node =
        NPDefault.parse(parser, ctx) {
            NodeMacroInline(token, it.toMutableList(), ctx.macros.reversed())
        }

    override fun unparse(node: NodeMacroInline, unparser: Unparser, ctx: UnparsingContext, indent: Int) =
        throw UnsupportedOperationException("Not yet implemented")

    override fun process(node: NodeMacroInline, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val names = processor.computeList(node.nodes[0], ctx).map { processor.computeString(it, ctx) }
        node.nodes.drop(1).forEach { expr ->
            expr as INodesList
            for (i in 0 until expr.nodes.size) {
                val it = expr.nodes[i]
                if (it is NodeMacroArg && names.any { name -> processor.computeString(it.nodes[0], ctx) == name }) {
                    sliceInsert(expr.nodes as MutableList<Any?>, i, (processor.process(it, ctx, mode)!! as INodesList).nodes)
                }
            }
        }
        return NRDefault.process(
            nodeProgn(node.token.line, node.nodes.drop(1).toMutableList()),
            processor,
            ctx,
            mode
        )
    }
}