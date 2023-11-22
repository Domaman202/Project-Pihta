package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.Platform
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.processor.utils.platform
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.base.utils.isPrimitive
import ru.DmN.pht.std.ast.NodeValue
import ru.DmN.pht.std.utils.computeString

object NRIs : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        VirtualType.BOOLEAN

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        return if (ctx.platform == Platform.JAVA) {
            val type = processor.computeString(node.nodes[0], ctx)
            val value = processor.calc(node.nodes[1], ctx)
            NodeValue.of(
                node.token.line,
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