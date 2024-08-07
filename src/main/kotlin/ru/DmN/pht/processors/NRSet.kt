package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeFSet
import ru.DmN.pht.ast.NodeMCall
import ru.DmN.pht.ast.NodeMCall.Type.STATIC
import ru.DmN.pht.ast.NodeMCall.Type.VIRTUAL
import ru.DmN.pht.ast.NodeSet
import ru.DmN.pht.processor.ctx.body
import ru.DmN.pht.processor.ctx.classes
import ru.DmN.pht.processor.ctx.method
import ru.DmN.pht.processor.utils.Static
import ru.DmN.pht.utils.node.NodeTypes.*
import ru.DmN.pht.utils.node.nodeGetOrName
import ru.DmN.pht.utils.node.nodeGetVariable
import ru.DmN.pht.utils.node.nodeValueClass
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.mapMutable
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType

object NRSet : INodeProcessor<NodeSet> {
    override fun process(node: NodeSet, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node =
        process(node.info, node.name, node.nodes, processor, ctx)

    fun process(info: INodeInfo, name: String, values: List<Node>, processor: Processor, ctx: ProcessingContext): Node {
        val value = values.mapMutable { processor.process(it, ctx, true)!! }
        ctx.body[name]?.let { return NodeSet(info.withType(SET_), value, name) }
        val classes = ctx.classes
        val static = !ctx.method.modifiers.static
        classes.forEach { it -> findSetter(info, it, name, value, static, processor, ctx)?.let { return it } }
        val field = classes.firstNotNullOf { it -> it.fields.find { it.name == name } }
        return NodeFSet(
            info.withType(FSET_),
            mutableListOf<Node>(
                if (field.modifiers.isStatic)
                    nodeValueClass(info, field.declaringClass.name)
                else nodeGetOrName(info, name)
            ).apply { addAll(value) },
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