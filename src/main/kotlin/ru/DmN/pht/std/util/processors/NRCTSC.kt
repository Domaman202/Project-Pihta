package ru.DmN.pht.std.util.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.processors.INodeProcessor
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.fp.ast.NodeValue

/***
 * Compile-Time String Constant
 */
class NRCTSC(val const: (processor: Processor, ctx: ProcessingContext) -> String) : INodeProcessor<Node> {
    override fun calc(node: Node, processor: Processor, ctx: ProcessingContext): VirtualType =
        VirtualType.STRING

    override fun process(node: Node, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeValue =
        NodeValue.of(node.tkOperation.line, NodeValue.Type.STRING, const(processor, ctx))
}