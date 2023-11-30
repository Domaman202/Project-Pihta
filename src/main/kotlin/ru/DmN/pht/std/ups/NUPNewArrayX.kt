package ru.DmN.pht.std.ups

import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.INUP
import ru.DmN.pht.std.ast.NodeNewArray

object NUPNewArrayX : INUP<NodeNewArray, NodeNewArray> {
    override fun unparse(node: NodeNewArray, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.run {
            append('(').append("new-array")
                .append('\n').append("\t".repeat(indent + 1)).append(NUPValueA.unparseType(node.type.name))
                .append('\n').append("\t".repeat(indent + 1))
            unparser.unparse(node.size, ctx, indent + 1)
            append(')')
        }
    }

    override fun calc(node: NodeNewArray, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.type
}