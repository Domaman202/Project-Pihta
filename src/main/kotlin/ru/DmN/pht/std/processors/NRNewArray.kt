package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeNewArray
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.compute
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.line

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