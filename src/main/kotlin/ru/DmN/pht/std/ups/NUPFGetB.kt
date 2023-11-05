package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeFGet
import ru.DmN.pht.std.ast.NodeFMGet
import ru.DmN.pht.std.ast.NodeMCall
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.nodeMCall
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.processors.NRFGetA
import ru.DmN.pht.std.processors.NRMCall
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.line

object NUPFGetB : INodeUniversalProcessor<Node, NodeFMGet> {
    override fun calc(node: NodeFMGet, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRFGetA.findField(getInstanceType(node, processor, ctx), node.name, node.static)?.type

    override fun process(node: NodeFMGet, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        return if (mode == ValType.VALUE) {
            val instance = processor.process(node.instance, ctx, ValType.VALUE)!!
            val type = getInstanceType(node, processor, ctx)!!
            val field = NRFGetA.findField(type, node.name, node.static)
            if (field == null)
                NRMCall.process(
                    nodeMCall(node.line, instance, "get${node.name.let { it[0].toUpperCase() + it.substring(1) }}", emptyList()),
                    processor,
                    ctx,
                    ValType.VALUE
                )
            else NodeFGet(
                Token(node.token.line, Token.Type.OPERATION, "!fget"),
                mutableListOf(instance),
                node.name,
                type.let {
                    if (field.static)
                        NodeFGet.Type.STATIC
                    else NodeFGet.Type.INSTANCE
                },
                type
            )
        } else null
    }

    private fun getInstanceType(node: NodeFMGet, processor: Processor, ctx: ProcessingContext) =
        if (node.static)
            ctx.global.getType(processor.computeString(node.instance, ctx), processor.tp)
        else processor.calc(node.instance, ctx)
}