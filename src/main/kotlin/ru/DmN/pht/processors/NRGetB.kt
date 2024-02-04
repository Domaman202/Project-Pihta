package ru.DmN.pht.processors

import ru.DmN.pht.processor.utils.Static
import ru.DmN.pht.ast.NodeGet
import ru.DmN.pht.ast.NodeGet.Type.*
import ru.DmN.pht.ast.NodeMCall
import ru.DmN.pht.ast.NodeMCall.Type.STATIC
import ru.DmN.pht.ast.NodeMCall.Type.VIRTUAL
import ru.DmN.pht.node.NodeTypes
import ru.DmN.pht.node.NodeTypes.GET_
import ru.DmN.pht.node.nodeGetVariable
import ru.DmN.pht.node.nodeValueClass
import ru.DmN.pht.processor.utils.*
import ru.DmN.pht.utils.computeString
import ru.DmN.pht.utils.forEach
import ru.DmN.pht.utils.InlineVariable
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.ValType.VALUE
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType

object NRGetB : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(
            process(
                node.info,
                processor.computeString(processor.process(node.nodes[0], ctx, VALUE)!!, ctx),
                node.nodes.asSequence().drop(1).computeValues(processor, ctx).toMutableList(),
                processor,
                ctx,
                VALUE
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
        ctx.body[name]?.let {
            return if (mode == VALUE)
                if (it is InlineVariable)
                    processor.process(it.value, ctx, VALUE)
                else NodeGet(info.withType(GET_), name, VARIABLE)
            else null
        }
        val clazz = ctx.clazz
        ctx.classes.forEach(clazz) { it -> findGetter(info, it, name, nodes, !ctx.method.modifiers.static, processor, ctx)?.let { return it } }
        if (nodes.isNotEmpty())
            throw RuntimeException("DEBUG")
        return if (mode == VALUE)
            NodeGet(
                info.withType(GET_),
                name,
                if ((clazz.fields.find { it.name == name } ?: ctx.classes.asSequence().map { it -> it.fields.find { it.name == name } }.first()!!).modifiers.isStatic)
                    THIS_STATIC_FIELD
                else THIS_FIELD
            )
        else null
    }

    fun findGetter(info: INodeInfo, type: VirtualType, name: String, nodes: List<Node>, allowVirtual: Boolean, processor: Processor, ctx: ProcessingContext): Node? { // todo: static / no static
        if (allowVirtual)
            findGetter(info, type, name, nodeGetVariable(info, "this"), nodes, VIRTUAL, processor, ctx)?.let { return it }
        return findGetter(info, type, name, nodeValueClass(info, type.name), nodes, STATIC, processor, ctx)
    }


    private fun findGetter(info: INodeInfo, type: VirtualType, name: String, instance: Node, nodes: List<Node>, call: NodeMCall.Type, processor: Processor, ctx: ProcessingContext): Node? =
        NRFGetB.findGetter(type, name, nodes, if (call == STATIC) Static.STATIC else Static.NO_STATIC, processor, ctx)?.let {
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