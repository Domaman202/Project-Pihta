package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.std.ast.NodeIncDec
import ru.DmN.pht.std.processor.utils.nodeMCall
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.line

object NRIncDec : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node = // todo: no-int inc/dec
        NodeIncDec(node.token.processed(), processor.computeString(node.nodes[0], ctx))
}