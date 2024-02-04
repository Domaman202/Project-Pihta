package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeRFn
import ru.DmN.pht.node.NodeTypes.RFN_
import ru.DmN.pht.processor.utils.global
import ru.DmN.pht.utils.*
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.ValType.NO_VALUE
import ru.DmN.siberia.processor.utils.ValType.VALUE
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType

object NRRFn : INodeProcessor<NodeNodesList> { // todo: двусторонний calc для аргументов (вычисление расстояния до типа)
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType(if (node.nodes[0].isConstClass) processor.computeString(node.nodes[0], ctx) else "Any", processor.tp)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeRFn? {
        if (mode == NO_VALUE)
            return null
        val typeNode = node.nodes[0]
        val type =
            if (typeNode.isLiteral && processor.computeString(typeNode, ctx) == ".")
                null
            else processor.computeType(node.nodes[0], ctx)
        val instance = processor.process(node.nodes[1], ctx, VALUE)!!
        val name = processor.computeString(node.nodes[2], ctx)
        return NodeRFn(node.info.withType(RFN_), type, null, instance, name, null).process(processor, ctx)
    }

    fun NodeRFn.process(processor: Processor, ctx: ProcessingContext): NodeRFn {
        if (type == null)
            return this
        lambda = findLambdaMethod(type!!)
        method =
            if (instance.isConstClass)
                findMethod(processor.computeType(instance, ctx), name, lambda!!, true)
            else findMethod(processor.calc(instance, ctx)!!, name, lambda!!, false)
        return this
    }

    private fun findMethod(instance: VirtualType, name: String, lambda: VirtualMethod, static: Boolean): VirtualMethod =
        instance.methods
            .asSequence()
            .filter { it.modifiers.static == static && it.name == name && it.argsc.size == lambda.argsc.size }
            .map { Pair(it, it.argsc.asSequence().mapIndexed { i, t -> lenArgsB(t, lambda.argsc[i]) }.sum()) }
            .sortedBy { it.second }
            .lastOrNull()
            ?.first
            ?: NRMCall.throwMNF(instance, name, lambda.argsc)
}