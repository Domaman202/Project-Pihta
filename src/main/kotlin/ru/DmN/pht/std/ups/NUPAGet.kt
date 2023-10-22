package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.utils.processNodes
import ru.DmN.pht.std.ast.NodeAGet

object NUPAGet : INodeUniversalProcessor<NodeAGet, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx, operationToken)

    override fun unparse(node: NodeAGet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.token.text).append('\n').append("\t".repeat(indent + 1))
            unparser.unparse(node.arr, ctx, indent + 1)
            append('\n').append("\t".repeat(indent + 1))
            unparser.unparse(node.index, ctx, indent + 1)
            append(')')
        }
    }

    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes[0], ctx)!!.componentType

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeAGet? =
        if (mode == ValType.VALUE) {
            val nodes = processor.processNodes(node, ctx, ValType.VALUE)
            NodeAGet(node.token, nodes[0], nodes[1])
        } else null
}