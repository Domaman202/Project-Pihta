package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeGet
import ru.DmN.pht.ast.NodeGet.Type.*
import ru.DmN.pht.ast.NodeMCall
import ru.DmN.pht.ast.NodeMCall.Type.STATIC
import ru.DmN.pht.ast.NodeMCall.Type.VIRTUAL
import ru.DmN.pht.processor.ctx.body
import ru.DmN.pht.processor.ctx.classes
import ru.DmN.pht.processor.ctx.method
import ru.DmN.pht.processor.utils.Static
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.processor.utils.computeValues
import ru.DmN.pht.processor.utils.processValues
import ru.DmN.pht.utils.InlineVariable
import ru.DmN.pht.utils.node.NodeTypes.GET_
import ru.DmN.pht.utils.node.NodeTypes.MCALL_
import ru.DmN.pht.utils.node.nodeGetVariable
import ru.DmN.pht.utils.node.nodeValueClass
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.exception.MessageException
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType

object NRGetB : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        processor.calc(
            process(
                node.info,
                processor.computeString(processor.process(node.nodes[0], ctx, true)!!, ctx),
                node.nodes.asSequence().drop(1).computeValues(processor, ctx).toMutableList(),
                processor,
                ctx,
                true
            )!!, ctx)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        process(
            node.info,
            processor.computeString(processor.process(node.nodes[0], ctx, valMode)!!, ctx),
            node.nodes.asSequence().drop(1).processValues(processor, ctx).toMutableList(),
            processor,
            ctx,
            valMode
        )

    fun process(info: INodeInfo, name: String, nodes: MutableList<Node>, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        ctx.body[name]?.let {
            return if (valMode)
                if (it is InlineVariable)
                    processor.process(it.value, ctx, true)
                else NodeGet(info.withType(GET_), name, VARIABLE, it.type)
            else null
        }
        val classes = ctx.classes
        classes.forEach{ it -> findGetter(info, it, name, nodes, !ctx.method.modifiers.static, processor, ctx)?.let { return it } }
        if (nodes.isNotEmpty())
            throw RuntimeException("DEBUG")
        return if (valMode) {
            val field = classes.firstNotNullOfOrNull { it -> it.fields.find { it.name == name } }
                ?: throw MessageException(null, "Переменная '$name' не найдена!")
            NodeGet(
                info.withType(GET_),
                name,
                if (field.modifiers.isStatic)
                    THIS_STATIC_FIELD
                else THIS_FIELD,
                field.type
            )
        } else null
    }

    fun findGetter(info: INodeInfo, type: VirtualType, name: String, nodes: List<Node>, allowVirtual: Boolean, processor: Processor, ctx: ProcessingContext): Node? { // todo: static / no static
        if (allowVirtual)
            findGetter(info, type, name, nodeGetVariable(info, "this", type), nodes, VIRTUAL, processor, ctx)?.let { return it }
        return findGetter(info, type, name, nodeValueClass(info, type.name), nodes, STATIC, processor, ctx)
    }


    private fun findGetter(info: INodeInfo, type: VirtualType, name: String, instance: Node, nodes: List<Node>, call: NodeMCall.Type, processor: Processor, ctx: ProcessingContext): Node? =
        NRFGetB.findGetter(type, name, nodes, if (call == STATIC) Static.STATIC else Static.NO_STATIC, processor, ctx)?.let {
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