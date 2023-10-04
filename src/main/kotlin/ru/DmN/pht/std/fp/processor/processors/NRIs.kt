package ru.DmN.pht.std.fp.processor.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.processors.INodeProcessor
import ru.DmN.pht.base.processor.utils.Platform
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.isPrimitive
import ru.DmN.pht.base.utils.platform
import ru.DmN.pht.std.base.utils.computeString
import ru.DmN.pht.std.fp.ast.NodeValue

object NRIs : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VirtualType.BOOLEAN

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        return if (ctx.platform == Platform.JAVA) {
            val type = processor.computeString(node.nodes[0], ctx)
            val value = processor.calc(node.nodes[1], ctx)
            NodeValue.of(
                node.tkOperation.line,
                NodeValue.Type.BOOLEAN,
                if (type.isPrimitive()) {
                    if (value?.isPrimitive == true)
                        (type == value.name).toString()
                    else "false"
                } else if (value?.isPrimitive == true)
                    "false"
                else return node
            )
        } else node
    }
}