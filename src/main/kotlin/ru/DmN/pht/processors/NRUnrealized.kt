package ru.DmN.pht.processors

import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.pht.utils.node.nodeThrow
import ru.DmN.pht.utils.node.nodeValue
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor

object NRUnrealized : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        when (ctx.platform) {
            JVM -> processor.process(
                nodeThrow(
                    node.info,
                    "java.lang.RuntimeException",
                    listOf(nodeValue(node.info, "Функция не реализована для вашей платформы."))
                ),
                ctx,
                false
            )

            else -> node
        }
}