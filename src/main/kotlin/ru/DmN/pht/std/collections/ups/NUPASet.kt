package ru.DmN.pht.std.collections.ups

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
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.utils.INodeUniversalProcessor
import ru.DmN.pht.std.base.utils.compute
import ru.DmN.pht.std.base.utils.processNodes
import ru.DmN.pht.std.collections.ast.NodeASet

object NUPASet : INodeUniversalProcessor<NodeASet, NodeNodesList> {
    override fun parse(parser: Parser, ctx: ParsingContext, operationToken: Token): Node? =
        NPDefault.parse(parser, ctx, operationToken)

    override fun unparse(node: NodeASet, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.apply {
            append('(').append(node.tkOperation.text).append('\n')
            unparser.unparse(node.arr, ctx, indent + 1)
            unparser.unparse(node.index, ctx, indent + 1)
            unparser.unparse(node.value, ctx, indent + 1)
            append(')')
        }
    }

    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(node.nodes[2], ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeASet {
        val nodes = processor.processNodes(node, ctx, ValType.VALUE)
        return NodeASet(node.tkOperation, nodes[0], nodes[1], nodes[2])
    }
}