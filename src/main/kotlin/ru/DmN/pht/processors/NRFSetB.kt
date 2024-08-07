package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeFSet
import ru.DmN.pht.ast.NodeFieldSet
import ru.DmN.pht.ast.NodeMCall
import ru.DmN.pht.ast.NodeMCall.Type.VIRTUAL
import ru.DmN.pht.ast.NodeValue
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.utils.Static
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.utils.node.NodeTypes.FSET_
import ru.DmN.pht.utils.node.NodeTypes.MCALL_
import ru.DmN.pht.utils.node.nodeAs
import ru.DmN.pht.utils.node.nodeValue
import ru.DmN.pht.utils.node.nodeValueClass
import ru.DmN.pht.utils.vtype.VTDynamic
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType

object NRFSetB : INodeProcessor<NodeFieldSet> {
    override fun process(node: NodeFieldSet, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node =
        process(node.info, node.name, node.instance, node.nodes, node.static, node.native, processor, ctx)

    fun process(info: INodeInfo, name: String, instance0: Node, values: List<Node>, static: Boolean, native: Boolean, processor: Processor, ctx: ProcessingContext): Node {
        val instance = processor.process(instance0, ctx, true)!!
            .let { if (static) nodeValueClass(info, processor.computeString(it, ctx)) else it }
        val type =
            if (static)
                ctx.global.getType((instance as NodeValue).value)
            else processor.calc(instance0, ctx)!!
        val result =
            if (native)
                null
            else if (type == VTDynamic) {
                val result = NRMCall.findMethod(
                    ctx.global.getType("ru.DmN.pht.utils.DynamicUtils"),
                    "invokeSetter",
                    values,
                    Static.ANY,
                    processor,
                    ctx
                )
                return NodeMCall(
                    info.withType(MCALL_),
                    NRMCall.processArguments(
                        info,
                        processor,
                        ctx,
                        result.method,
                        listOf(instance, nodeValue(info, name)) + values,
                        result.compression
                    ),
                    null,
                    nodeValueClass(info, result.method.declaringClass.name),
                    result.method,
                    VIRTUAL
                )
            } else findSetter(type, name, values, if (static) Static.STATIC else Static.NO_STATIC, processor, ctx)
        return if (result == null) {
            val field = type.fields.find { it.name == name }!!
            NodeFSet(
                info.withType(FSET_),
                mutableListOf(instance, processor.process(nodeAs(info, values.first(), field.type.name), ctx, true)!!),
                field
            )
        } else NodeMCall(
            info.withType(MCALL_),
            NRMCall.processArguments(info, processor, ctx, result.method, values, result.compression),
            null,
            instance,
            result.method,
            VIRTUAL
        )
    }

    fun findSetter(type: VirtualType, name: String, args: List<Node>, static: Static, processor: Processor, ctx: ProcessingContext) =
        NRMCall.findMethodOrNull(
            type,
            "set${name.let { it[0].uppercase() + it.substring(1) }}",
            args,
            static,
            processor,
            ctx
        )
}