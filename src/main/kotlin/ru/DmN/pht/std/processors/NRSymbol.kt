package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.utils.computeString

object NRSymbol : IStdNodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == ValType.VALUE)
            NodeValue.of(node.token.line, NodeValue.Type.STRING, computeString(node, processor, ctx))
        else null

    override fun computeString(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): String =
        node.nodes.map { processor.computeString(it, ctx) }.reduce { acc, s -> acc + s }
}