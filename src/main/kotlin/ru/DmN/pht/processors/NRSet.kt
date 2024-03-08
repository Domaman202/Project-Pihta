package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeFSet
import ru.DmN.pht.ast.NodeMCall
import ru.DmN.pht.ast.NodeMCall.Type.STATIC
import ru.DmN.pht.ast.NodeMCall.Type.VIRTUAL
import ru.DmN.pht.ast.NodeSet
import ru.DmN.pht.processor.ctx.body
import ru.DmN.pht.processor.ctx.classes
import ru.DmN.pht.processor.ctx.clazz
import ru.DmN.pht.processor.ctx.method
import ru.DmN.pht.processor.utils.Static
import ru.DmN.pht.processor.utils.processValues
import ru.DmN.pht.utils.forEach
import ru.DmN.pht.utils.node.NodeTypes
import ru.DmN.pht.utils.node.NodeTypes.MCALL_
import ru.DmN.pht.utils.node.nodeGetOrName
import ru.DmN.pht.utils.node.nodeGetVariable
import ru.DmN.pht.utils.node.nodeValueClass
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType

object NRSet : INodeProcessor<NodeSet> {
    override fun process(node: NodeSet, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node {
        val info = node.info
        val value = node.nodes.asSequence().processValues(processor, ctx).toMutableList()
        ctx.body[node.name]?.let { return NodeSet(info.withType(NodeTypes.SET_), value, node.name) }
        val clazz = ctx.clazz
        ctx.classes.forEach(clazz) { it ->
            findSetter(info, it, node.name, value, !ctx.method.modifiers.static, processor, ctx)?.let { return it }
        }
        val field = clazz.fields.find { it.name == node.name } ?: ctx.classes.asSequence().map { it -> it.fields    .find { it.name == node.name } }.first()!!
        return NodeFSet(
            info.withType(NodeTypes.FSET_),
            mutableListOf<Node>(if (field.modifiers.isStatic) nodeValueClass(info, field.declaringClass.name) else nodeGetOrName(info, node.name)).apply { addAll(value) },
            field
        )
    }

    private fun findSetter(info: INodeInfo, type: VirtualType, name: String, values: List<Node>, allowVirtual: Boolean, processor: Processor, ctx: ProcessingContext): Node? {
        if (allowVirtual)
            findSetter(info, type, name, nodeGetVariable(info, "this", type), values, VIRTUAL, processor, ctx)?.let { return it }
        return findSetter(info, type, name, nodeValueClass(info, type.name), values, STATIC, processor, ctx)
    }


    private fun findSetter(info: INodeInfo, type: VirtualType, name: String, instance: Node, value: List<Node>, call: NodeMCall.Type, processor: Processor, ctx: ProcessingContext): Node? =
        NRFSetB.findSetter(type, name, value, if (call == STATIC) Static.STATIC else Static.NO_STATIC, processor, ctx)?.let {
            NodeMCall(
                info.withType(MCALL_),
                NRMCall.processArguments(info, processor, ctx, it.method, it.args, it.compression),
                null,
                instance,
                it.method,
                call
            )
        }
}