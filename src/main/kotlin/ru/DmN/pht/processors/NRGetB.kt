package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeGet
import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRGetB : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(process(node, processor, ctx, ValType.VALUE)!!, ctx)

    fun calc(info: INodeInfo, name: String, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(process(info, name, processor, ctx, ValType.VALUE)!!, ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        process(node.info, processor.computeString(processor.process(node.nodes[0], ctx, mode)!!, ctx), processor, ctx, mode)

    fun process(info: INodeInfo, name: String, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        ctx.body[name]?.let { return if (mode == ValType.VALUE) NodeGet(info.withType(NodeTypes.GET_), name, NodeGet.Type.VARIABLE) else null }
        val clazz = ctx.clazz
        findGetter(info, clazz, name, !ctx.method.modifiers.static, processor, ctx)?.let { return it }
        return if (mode == ValType.VALUE) {
            val field = clazz.fields.find { it.name == name }!!
            NodeGet(
                info.withType(NodeTypes.GET_),
                name,
                if (field.modifiers.isStatic)
                    NodeGet.Type.THIS_STATIC_FIELD
                else NodeGet.Type.THIS_FIELD
            )
        } else null
    }

    private fun findGetter(info: INodeInfo, type: VirtualType, name: String, allowVirtual: Boolean, processor: Processor, ctx: ProcessingContext): Node? {
        if (allowVirtual)
            findGetter(info, type, name, nodeGetOrName(info, "this"), NodeMCall.Type.VIRTUAL, processor, ctx)?.let { return it }
        return findGetter(info, type, name, nodeValueClass(info, type.name), NodeMCall.Type.STATIC, processor, ctx)
    }


    private fun findGetter(info: INodeInfo, type: VirtualType, name: String, instance: Node, call: NodeMCall.Type, processor: Processor, ctx: ProcessingContext): Node? =
        NRFGetB.findGetter(type, name, processor, ctx)?.let {
            NodeMCall(
                info.withType(NodeTypes.MCALL_),
                NRMCall.processArguments(info, processor, ctx, it.method, it.args, it.compression),
                null,
                instance,
                it.method,
                call
            )
        }
}