package ru.DmN.pht.std.oop.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.std.fp.ast.NodeFSet
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor
import ru.DmN.pht.std.base.utils.computeString

object NUPFSetA : INodeUniversalProcessor<NodeFSet, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx, operationToken)

    override fun unparse(node: NodeFSet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append(' ')
            unparser.unparse(node.nodes[0], ctx, indent + 1)
            append(' ').append(node.name).append(' ')
            unparser.unparse(node.nodes[1], ctx, indent + 1)
            append(')')
        }
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeFSet {
        val name = processor.computeString(node.nodes[1], ctx)
        return NodeFSet(
            node.tkOperation,
            mutableListOf(processor.process(node.nodes[0], ctx, ValType.VALUE)!!, processor.process(node.nodes[2], ctx, ValType.NO_VALUE)!!),
            name,
            if (node.nodes[0].isConstClass())
                NodeFSet.Type.STATIC
            else if (processor.calc(node.nodes[0], ctx)!!.fields.find { it.name == name } != null)
                NodeFSet.Type.INSTANCE
            else NodeFSet.Type.UNKNOWN
        )
    }
}