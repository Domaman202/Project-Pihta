package ru.DmN.pht.processors

import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.pht.utils.node.nodeArrayOfType
import ru.DmN.pht.utils.node.nodeMCall
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRListOf : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        ctx.global.getType("List", processor.tp)

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        if (valMode)
            when (ctx.platform) {
                JVM -> {
                    val info = node.info
                    NRMCall.process(
                        nodeMCall(
                            info,
                            nodeArrayOfType(info, "Any", node.nodes),
                            "toList",
                            emptyList()
                        ),
                        processor,
                        ctx,
                        true
                    )
                }

                else -> node
            }
        else null
}