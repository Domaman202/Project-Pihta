package ru.DmN.pht.base.processor.processors

import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.Processor

interface INodeProcessor<T : Node> {
    fun process(node: T, processor: Processor, ctx: ProcessingContext): Node
}