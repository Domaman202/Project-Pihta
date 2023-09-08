package ru.DmN.pht.base.processor.processors

import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.Processor

object NRDefault : INodeProcessor<Node> {
    override fun process(node: Node, processor: Processor, ctx: ProcessingContext): Node =
        node
}