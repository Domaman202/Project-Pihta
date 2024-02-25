package ru.DmN.pht.processors

import ru.DmN.pht.node.nodeThrow
import ru.DmN.pht.node.nodeValue
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.ValType.NO_VALUE
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor

object NRUnrealized : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        when (ctx.platform) {
            JVM -> NRThrow.process(
                nodeThrow(
                    node.info,
                    "java.lang.RuntimeException",
                    listOf(nodeValue(node.info, "Функция не реализована для вашей платформы."))
                ),
                processor,
                ctx,
                NO_VALUE
            )

            else -> node
        }
}