package ru.DmN.pht.base.processor.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeUse
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.utils.Module

object NRUse : INodeProcessor<NodeUse> {
    override fun process(node: NodeUse, processor: Processor, ctx: ProcessingContext): Node =
        node.apply { names.forEach { Module.MODULES[it]!!.inject(processor, ctx) } }
}