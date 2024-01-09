package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeRFn
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processors.NRMCall
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.computeType
import ru.DmN.pht.std.utils.findLambdaMethod
import ru.DmN.pht.std.utils.isConstClass
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType

object NRRFn : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        processor.computeType(node.nodes[0], ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeRFn {
        val type = processor.computeType(node.nodes[0], ctx)
        val lambda = findLambdaMethod(type)
        val instance = processor.process(node.nodes[1], ctx, ValType.VALUE)!!
        val name = processor.computeString(node.nodes[2], ctx)
        val method =
            if (instance.isConstClass)
                findMethod(processor.computeType(instance, ctx), name, lambda, true)
            else findMethod(processor.calc(instance, ctx)!!, name, lambda, false)
        return NodeRFn(node.info.withType(NodeTypes.RFN_), type, lambda, instance, method)
    }

    private fun findMethod(instance: VirtualType, name: String, lambda: VirtualMethod, static: Boolean): VirtualMethod =
        instance.methods.find {it.modifiers.static == static && it.name == name && it.argsc == lambda.argsc }
            ?: NRMCall.throwMNF(instance, name, lambda.argsc)
}