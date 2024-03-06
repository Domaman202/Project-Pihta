package ru.DmN.pht.processors

import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.pht.utils.node.nodeCls
import ru.DmN.pht.utils.node.nodeDefn
import ru.DmN.pht.utils.node.nodeStatic
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.IPlatform.UNIVERSAL

object NRFileFn : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node =
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
                        nodeStatic(
                            info,
                            nodeDefn(
                                info,
                                "<clinit>",
                                "void",
                                nodeStatic(
                                    info,
                                    node.nodes
                                )
                            )
                        )
                    ),
                    processor,
                    ctx,
                    false
                )
            }

            UNIVERSAL -> node
            else -> throw UnsupportedOperationException()
        }
}