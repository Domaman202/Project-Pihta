package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeRFn
import ru.DmN.pht.processors.NRRFn.process
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.*
import ru.DmN.siberia.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType

object NRRFnB : INodeProcessor<NodeRFn>, IAdaptableProcessor<NodeRFn> {
    override fun calc(node: NodeRFn, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.type ?: ctx.global.getType("Any", processor.tp)

    override fun adaptableTo(type: VirtualType, node: NodeRFn, processor: Processor, ctx: ProcessingContext): Int {
        val static = node.instance.isConstClass
        return findMethod(
            if (static)
                processor.computeType(node.instance, ctx)
            else processor.calc(node.instance, ctx)!!,
            node.name,
            findLambdaMethod(type),
            static
        )
    }

    override fun adaptToType(type: VirtualType, node: NodeRFn, processor: Processor, ctx: ProcessingContext): NodeRFn =
        node.apply { this.type = type }.process(processor, ctx)

    private fun findMethod(instance: VirtualType, name: String, lambda: VirtualMethod, static: Boolean): Int =
        instance.methods
            .asSequence()
            .filter { it.modifiers.static == static && it.name == name && it.argsc.size == lambda.argsc.size }
            .map { it -> it.argsc.asSequence().mapIndexed { i, t -> lenArgsB(t, lambda.argsc[i]) }.sum() }
            .sorted()
            .firstOrNull()
            ?: -1
}