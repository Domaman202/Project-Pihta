package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeNewArray
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.compute
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.lexer.Token
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.line

object NRNewArray : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType(processor.computeString(node.nodes[0], ctx), processor.tp).arrayType

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeNewArray? =
        if (mode == ValType.VALUE)
            NodeNewArray(
                Token.operation(node.line, "!new-array"),
                ctx.global.getType(processor.computeString(node.nodes[0], ctx), processor.tp),
                processor.compute(node.nodes[1], ctx)
            )
        else null
}