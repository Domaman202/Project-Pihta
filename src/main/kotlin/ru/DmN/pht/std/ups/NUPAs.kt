package ru.DmN.pht.std.ups

import ru.DmN.pht.std.ast.NodeAs
import ru.DmN.pht.std.unparsers.NUDefaultX
import ru.DmN.pht.std.utils.IStdNUP
import ru.DmN.siberia.Processor
import ru.DmN.siberia.Unparser
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.unparser.UnparsingContext
import ru.DmN.siberia.utils.VirtualType

object NUPAs : IStdNUP<NodeAs, NodeAs> {
    override fun unparse(node: NodeAs, unparser: Unparser, ctx: UnparsingContext, indent: Int) {
        unparser.out.run {
            append('(').append(NUDefaultX.text(node.token)).append(' ').append(NUPValueA.unparseType(node.type.name)).append('\n').append("\t".repeat(indent + 1))
            unparser.unparse(node.nodes[0], ctx, indent + 1)
            append(')')
        }
    }

    override fun calc(node: NodeAs, processor: Processor, ctx: ProcessingContext): VirtualType =
        node.type
}