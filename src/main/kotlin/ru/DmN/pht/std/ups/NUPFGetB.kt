package ru.DmN.pht.std.ups

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeFGet
import ru.DmN.pht.std.ast.NodeFMGet
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processors.INodeUniversalProcessor
import ru.DmN.pht.std.processors.NRFGetA
import ru.DmN.pht.std.utils.computeString

object NUPFGetB : INodeUniversalProcessor<Node, NodeFMGet> {
    override fun calc(node: NodeFMGet, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRFGetA.findField(getInstanceType(node, processor, ctx), node.name, node.static)?.type

    override fun process(node: NodeFMGet, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeFGet? {
        return if (mode == ValType.VALUE) {
            val instance = processor.process(node.instance, ctx, ValType.VALUE)!!
            NodeFGet(
                Token(node.token.line, Token.Type.OPERATION, "!fget"),
                mutableListOf(instance),
                node.name,
                getInstanceType(node, processor, ctx).let {
                    val field = NRFGetA.findField(it, node.name, node.static)
                    if (field == null)
                        NodeFGet.Type.UNKNOWN
                    else if (field.static)
                        NodeFGet.Type.STATIC
                    else NodeFGet.Type.INSTANCE
                }
            )
        } else null
    }

    private fun getInstanceType(node: NodeFMGet, processor: Processor, ctx: ProcessingContext) =
        if (node.static)
            ctx.global.getType(processor.computeString(node.instance, ctx), processor.tp)
        else processor.calc(node.instance, ctx)
}