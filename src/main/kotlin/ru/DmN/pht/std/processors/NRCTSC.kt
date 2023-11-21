package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.processor.utils.global

/***
 * Compile-Time String Constant
 */
class NRCTSC(val const: (processor: Processor, ctx: ProcessingContext) -> String) : INodeProcessor<Node> {
    override fun calc(node: Node, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType("String", processor.tp)

    override fun process(node: Node, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeValue =
        NodeValue.of(node.token.line, NodeValue.Type.STRING, const(processor, ctx))
}