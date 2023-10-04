package ru.DmN.pht.std.oop.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.processors.INodeProcessor
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.fp.ast.NodeFGet
import ru.DmN.pht.std.fp.ast.NodeFMGet
import ru.DmN.pht.std.base.processor.utils.global
import ru.DmN.pht.std.base.utils.computeString
import ru.DmN.pht.std.oop.ups.NUPFGetA

object NRFGetB : INodeProcessor<NodeFMGet> {
    override fun calc(node: NodeFMGet, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NUPFGetA.findField(getInstanceType(node, processor, ctx), node.name, node.static)?.type

    override fun process(node: NodeFMGet, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeFGet? {
        return if (mode == ValType.VALUE) {
            val instance = processor.process(node.instance, ctx, ValType.VALUE)!!
            NodeFGet(
                Token(node.tkOperation.line, Token.Type.OPERATION, "fget"),
                mutableListOf(instance),
                node.name,
                getInstanceType(node, processor, ctx).let {
                    val field = NUPFGetA.findField(it, node.name, node.static)
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