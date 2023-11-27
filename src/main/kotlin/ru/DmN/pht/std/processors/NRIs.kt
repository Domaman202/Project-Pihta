package ru.DmN.pht.std.processors

import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.utils.Platform
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.isPrimitive
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