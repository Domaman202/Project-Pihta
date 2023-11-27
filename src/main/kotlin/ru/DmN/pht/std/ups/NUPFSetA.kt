package ru.DmN.pht.std.ups

import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.parsers.NPDefault
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.pht.std.ast.NodeFSet
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.isConstClass

object NUPFSetA : INodeUniversalProcessor<NodeFSet, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, token: Token): Node? =
        NPDefault.parse(parser, ctx, token)

    override fun unparse(node: NodeFSet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.token.text).append(' ')
            unparser.unparse(node.nodes[0], ctx, indent + 1)
            append(' ').append(node.name).append(' ')
            unparser.unparse(node.nodes[1], ctx, indent + 1)
            append(')')
        }
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeFSet {
        val name = processor.computeString(node.nodes[1], ctx)
        val type = if (node.nodes[0].isConstClass) ctx.global.getType(processor.computeString(node.nodes[0], ctx), processor.tp) else processor.calc(node.nodes[0], ctx)!!
        return NodeFSet(
            node.token,
            mutableListOf(processor.process(node.nodes[0], ctx, ValType.VALUE)!!, processor.process(node.nodes[2], ctx, ValType.NO_VALUE)!!),
            name,
            if (node.nodes[0].isConstClass)
                NodeFSet.Type.STATIC
            else if (processor.calc(node.nodes[0], ctx)!!.fields.find { it.name == name } != null)
                NodeFSet.Type.INSTANCE
            else NodeFSet.Type.UNKNOWN,
            type
        )
    }
}