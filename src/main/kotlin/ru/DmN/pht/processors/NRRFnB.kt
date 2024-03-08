package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeRFn
import ru.DmN.pht.processor.ctx.classes
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processors.NRRFn.process
import ru.DmN.pht.utils.computeType
import ru.DmN.pht.utils.findLambdaMethodOrNull
import ru.DmN.pht.utils.isConstClass
import ru.DmN.pht.utils.lenArgsB
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

object NRRFnB : INodeProcessor<NodeRFn>, IAdaptableProcessor<NodeRFn> {
    override fun calc(node: NodeRFn, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.type ?: ctx.global.getType("Any", processor.tp)

    override fun adaptableTo(type: VirtualType, node: NodeRFn, processor: Processor, ctx: ProcessingContext): Int {
        val name = node.name
        val lambda = findLambdaMethodOrNull(type) ?: return -1
        return if (node.instance != null) {
            val static = node.instance!!.isConstClass
            findMethod(
                if (static)
                    processor.computeType(node.instance!!, ctx)
                else processor.calc(node.instance!!, ctx)!!,
                name,
                lambda,
                static
            )
        } else ctx.classes
            .asSequence()
            .map { NRRFn.findMethodOrNull(it, name, lambda, true) }
            .filterNotNull()
            .map { it.argsc.asSequence().mapIndexed { i, t -> lenArgsB(t, lambda.argsc[i]) }.sum() }
            .sorted()
            .firstOrNull()
            ?: -1
    }

    override fun adaptToType(type: VirtualType, node: NodeRFn, processor: Processor, ctx: ProcessingContext): NodeRFn =
        node.apply { this.type = type }.process(processor, ctx)

    private fun findMethod(instance: VirtualType, name: String, lambda: VirtualMethod, static: Boolean): Int =
        instance.methods
            .asSequence()
            .filter { it.modifiers.static == static && it.name == name && it.argsc.size == lambda.argsc.size }
            .map { it.argsc.asSequence().mapIndexed { i, t -> lenArgsB(t, lambda.argsc[i]) }.sum() }
            .sorted()
            .firstOrNull()
            ?: -1
}