package ru.DmN.pht.std.processors

import ru.DmN.pht.processor.utils.Static
import ru.DmN.pht.std.ast.NodeGetA
import ru.DmN.pht.std.ast.NodeGetB
import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.node.nodeGetOrName
import ru.DmN.pht.std.node.nodeValueClass
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.forEach
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
        processor.calc(
            process(
                node.info,
                processor.computeString(processor.process(node.nodes[0], ctx, ValType.VALUE)!!, ctx),
                node.nodes.asSequence().drop(1).computeValues(processor, ctx).toMutableList(),
                processor,
                ctx,
                ValType.VALUE
            )!!, ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        process(
            node.info,
            processor.computeString(processor.process(node.nodes[0], ctx, mode)!!, ctx),
            node.nodes.asSequence().drop(1).processValues(processor, ctx).toMutableList(),
            processor,
            ctx,
            mode
        )

    fun process(info: INodeInfo, name: String, nodes: MutableList<Node>, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        ctx.body[name]?.let { return if (mode == ValType.VALUE) NodeGetB(info.withType(NodeTypes.GET_), name, NodeGetA.Type.VARIABLE) else null }
        val clazz = ctx.clazz
        ctx.classes.forEach(clazz) { it ->
            findGetter(info, it, name, nodes, !ctx.method.modifiers.static, processor, ctx)?.let { return it }
        }
        return if (mode == ValType.VALUE)
            NodeGetA(
                info.withType(NodeTypes.GET_),
                nodes,
                name,
                if ((clazz.fields.find { it.name == name } ?: ctx.classes.asSequence().map { it -> it.fields.find { it.name == name } }.first()!!).modifiers.isStatic)
                    NodeGetA.Type.THIS_STATIC_FIELD
                else NodeGetA.Type.THIS_FIELD
            )
        else null
    }

    fun findGetter(info: INodeInfo, type: VirtualType, name: String, nodes: List<Node>, allowVirtual: Boolean, processor: Processor, ctx: ProcessingContext): Node? { // todo: static / no static
        if (allowVirtual)
            findGetter(info, type, name, nodeGetOrName(info, "this"), nodes, NodeMCall.Type.VIRTUAL, processor, ctx)?.let { return it }
        return findGetter(info, type, name, nodeValueClass(info, type.name), nodes, NodeMCall.Type.STATIC, processor, ctx)
    }


    private fun findGetter(info: INodeInfo, type: VirtualType, name: String, instance: Node, nodes: List<Node>, call: NodeMCall.Type, processor: Processor, ctx: ProcessingContext): Node? =
        NRFGetB.findGetter(type, name, nodes, if (call == NodeMCall.Type.STATIC) Static.STATIC else Static.NO_STATIC, processor, ctx)?.let {
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