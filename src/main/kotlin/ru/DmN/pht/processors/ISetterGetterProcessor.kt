package ru.DmN.pht.processors

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor

interface ISetterGetterProcessor<T : Node> : INodeProcessor<T> { // todo: normal docs
    /**
     *
     */
    fun processAsSetterLazy(node: T, values: List<Node>, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node?

    /**
     *
     */
    fun processAsSetter(node: T, values: List<Node>, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node?

    /**
     *
     */
    fun processAsGetter(node: T, processor: Processor, ctx: ProcessingContext): Node?
}