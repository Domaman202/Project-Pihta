package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeRFn
import ru.DmN.pht.processor.ctx.classes_seq
import ru.DmN.pht.processor.ctx.clazz
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.ctx.method
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.processor.utils.computeType
import ru.DmN.pht.utils.findLambdaMethod
import ru.DmN.pht.utils.isConstClass
import ru.DmN.pht.utils.isLiteral
import ru.DmN.pht.utils.lenArgsB
import ru.DmN.pht.utils.node.NodeTypes.RFN_
import ru.DmN.pht.utils.node.nodeGetVariable
import ru.DmN.pht.utils.node.nodeValueClass
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

object NRRFn : INodeProcessor<NodeNodesList> { // todo: двусторонний calc для аргументов (вычисление расстояния до типа)
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.nodes[0].let {
            if (processor.computeString(it, ctx) == ".")
                ctx.global.getType("Any")
            else processor.computeType(it, ctx)
        }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeRFn? =
        if (valMode) NodeRFn(
            node.info.withType(RFN_),
            node.nodes[0].let {
                if (processor.computeString(it, ctx) == ".")
                    null
                else processor.computeType(it, ctx)
            },
            null,
            node.nodes[1].let {
                if (it.isLiteral && processor.computeString(it, ctx) == ".")
                    null
                else processor.process(it, ctx, true)!!
            },
            processor.computeString(node.nodes[2], ctx),
            null
        ).process(processor, ctx)
        else null

    fun NodeRFn.process(processor: Processor, ctx: ProcessingContext): NodeRFn {
        if (type == null)
            return this
        lambda = findLambdaMethod(type!!)
        method = run {
            if (instance != null)
                if (instance!!.isConstClass)
                    findMethod(processor.computeType(instance!!, ctx), name, lambda!!, true)
                else findMethod(processor.calc(instance!!, ctx)!!, name, lambda!!, false)
            else {
                if (!ctx.method.modifiers.static) {
                    val instance = nodeGetVariable(info, "this", ctx.clazz)
                    findMethodOrNull(processor.calc(instance, ctx)!!, name, lambda!!, false)?.let {
                        this.instance = instance
                        return@run it
                    }
                }
                //
                val pair = ctx.classes_seq
                    .map { Pair(it, findMethodOrNull(it, name, lambda!!, true)) }
                    .filter { it.second != null }
                    .first()
                instance = nodeValueClass(info, pair.first.name)
                pair.second
            }
        }
        return this
    }

    private fun findMethod(instance: VirtualType, name: String, lambda: VirtualMethod, static: Boolean): VirtualMethod =
        findMethodOrNull(instance, name, lambda, static) ?: NRMCall.throwMNF(instance, name, lambda.argsc)

    fun findMethodOrNull(instance: VirtualType, name: String, lambda: VirtualMethod, static: Boolean): VirtualMethod? =
        instance.methods
            .asSequence()
            .filter { it.modifiers.static == static && it.name == name && it.argsc.size == lambda.argsc.size }
            .map { Pair(it, it.argsc.asSequence().mapIndexed { i, t -> lenArgsB(t, lambda.argsc[i]) }.sum()) }
            .sortedBy { it.second }
            .lastOrNull()
            ?.first
}
