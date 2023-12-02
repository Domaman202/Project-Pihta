package ru.DmN.pht.std.ups

import ru.DmN.pht.std.ast.NodeFGet
import ru.DmN.pht.std.ast.NodeFMGet
import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.nodeClass
import ru.DmN.pht.std.processor.utils.nodeValueOf
import ru.DmN.pht.std.processors.NRFGetA
import ru.DmN.pht.std.processors.NRMCall
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.utils.*

object NUPFGetB : INUP<Node, NodeFMGet> {
    override fun calc(node: NodeFMGet, processor: Processor, ctx: ProcessingContext): VirtualType? =
        getMethod(node, processor, ctx).third?.rettype ?: NRFGetA.findField(getInstanceType(node, processor, ctx), node.name, node.static)?.type

    override fun process(node: NodeFMGet, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == ValType.VALUE) {
            val line = node.line
            val instance = processor.process(node.instance, ctx, ValType.VALUE)!!
            val result = getMethod(node, processor, ctx)
            if (result.second == null)
                NodeFGet(
                    Token(line, Token.Type.OPERATION, "!fget"),
                    mutableListOf(instance),
                    node.name,
                    result.first.let {
                        val field = NRFGetA.findField(it, node.name, node.static)
                        if (field == null)
                            NodeFGet.Type.UNKNOWN
                        else if (field.isStatic)
                            NodeFGet.Type.STATIC
                        else NodeFGet.Type.INSTANCE
                    },
                    result.first
                )
            else if (result.first == VTDynamic)
                NodeMCall(
                    Token.operation(line, "!mcall"),
                    NRMCall.processArguments(line, processor, ctx, result.third!!, listOf(instance, nodeValueOf(line, node.name)) + node.nodes),
                    emptyList(),
                    nodeClass(line, result.third!!.declaringClass!!.name),
                    result.third!!,
                    NodeMCall.Type.VIRTUAL
                )
            else NodeMCall(
                Token.operation(line, "!mcall"),
                NRMCall.processArguments(line, processor, ctx, result.third!!, node.nodes),
                emptyList(),
                instance,
                result.third!!,
                NodeMCall.Type.VIRTUAL
            )
        } else null

    private fun getMethod(node: NodeFMGet, processor: Processor, ctx: ProcessingContext): Triple<VirtualType, List<Node>?, VirtualMethod?> {
        val type = getInstanceType(node, processor, ctx)!!
        return if (node.native)
            Triple(type, null, null)
        else {
            val result =
                if (type == VTDynamic)
                    NRMCall.findMethod(
                        ctx.global.getType("ru.DmN.pht.std.utils.DynamicUtils", processor.tp),
                        "invokeGetter",
                        node.nodes,
                        processor,
                        ctx
                    )
                else NRMCall.findMethodOrNull(
                    type,
                    "get${node.name.let { it[0].toUpperCase() + it.substring(1) }}",
                    emptyList(),
                    processor,
                    ctx
                )
            Triple(type, result?.first, result?.second)
        }
    }

    private fun getInstanceType(node: NodeFMGet, processor: Processor, ctx: ProcessingContext) =
        if (node.static)
            ctx.global.getType(processor.computeString(node.instance, ctx), processor.tp)
        else processor.calc(node.instance, ctx)
}