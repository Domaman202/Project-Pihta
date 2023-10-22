package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeMath
import ru.DmN.pht.std.processor.utils.nodeAs
import ru.DmN.pht.std.processor.utils.nodeMCall
import ru.DmN.pht.std.ups.NUPAs
import ru.DmN.pht.std.ups.NUPMCallA
import ru.DmN.pht.std.utils.line
import ru.DmN.pht.std.utils.processNodes

object NRMath : INodeProcessor<NodeMath> {
    override fun calc(node: NodeMath, processor: Processor, ctx: ProcessingContext): VirtualType? {
        val firstType = processor.calc(node.nodes[0], ctx)
        return if (firstType!!.isPrimitive)
            firstType
        else NUPMCallA.calc(
            nodeMCall(
                node.token.line,
                node.nodes[0],
                node.operation.name.toLowerCase(),
                node.nodes.drop(1)
            ), processor, ctx
        )
    }

    override fun process(node: NodeMath, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        val nodes = processor.processNodes(node, ctx, ValType.VALUE)
        val firstType = processor.calc(nodes[0], ctx)
        return if (firstType!!.isPrimitive)
            if (mode == ValType.VALUE) { // todo: primitive extensions
                val line = node.line
                NodeMath(
                    node.token.processed(),
                    nodes.map { NRAs.process(nodeAs(line, it, firstType.name), processor, ctx, ValType.VALUE)!! }.toMutableList(),
                    node.operation
                )
            } else null
        else NUPMCallA.process(
            nodeMCall(
                node.line,
                nodes[0],
                node.operation.name.toLowerCase(),
                nodes.drop(1)
            ), processor, ctx, ValType.VALUE
        )
    }
}