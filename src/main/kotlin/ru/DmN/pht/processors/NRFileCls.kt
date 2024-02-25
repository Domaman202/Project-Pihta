package ru.DmN.pht.processors

import ru.DmN.pht.node.nodeCls
import ru.DmN.pht.node.nodeStatic
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.Platforms.JVM
import ru.DmN.siberia.processor.utils.Platforms.UNIVERSAL
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.ValType.NO_VALUE
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor

object NRFileCls : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        when (ctx.platform) {
            JVM -> {
                val info = node.info
                NRClass.process(
                    nodeCls(
                        info,
                        info.file!!
                            .let { it.substring(it.lastIndexOf('/') + 1, it.lastIndexOf('.')) }
                            .let { it.first().uppercase() + it.drop(1) },
                        "java.lang.Object",
                        nodeStatic(info, node.nodes)
                    ),
                    processor,
                    ctx,
                    NO_VALUE
                )
            }

            UNIVERSAL -> node
            else -> throw UnsupportedOperationException()
        }
}