package ru.DmN.phtx.ppl.processors

import ru.DmN.pht.node.nodeGetOrName
import ru.DmN.pht.node.nodeMCall
import ru.DmN.pht.node.nodeNew
import ru.DmN.pht.node.nodeValue
import ru.DmN.pht.processors.NRMCall
import ru.DmN.pht.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualType
import javax.imageio.ImageIO

object NRIncImg : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        processor.tp.typeOf("java.awt.image.BufferedImage")

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == ValType.VALUE) {
            val info = node.info
            NRMCall.process(
                nodeMCall(
                    info,
                    "javax.imageio.ImageIO",
                    "read",
                    listOf(
                        nodeNew(
                            info,
                            "java.io.File",
                            listOf(nodeValue(info, "${ctx.module.name}/${processor.computeString(node.nodes[0], ctx)}"))
                        )
                    )
                ),
                processor,
                ctx,
                ValType.VALUE
            )
        } else null
}