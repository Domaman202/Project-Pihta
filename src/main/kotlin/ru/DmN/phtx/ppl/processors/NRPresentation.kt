package ru.DmN.phtx.ppl.processors

import ru.DmN.pht.node.*
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.processors.NRUseCtx
import ru.DmN.siberia.utils.VirtualType

object NRPresentation : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType =
        processor.tp.typeOf("ru.DmN.phtx.ppl.utils.Presentation")

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (mode == ValType.VALUE) {
            val info = node.info
            val args = listOf(node.nodes[0], node.nodes[1])
            val body = mutableListOf<Node>(nodeDef(
                info,
                "phtx\$ppl\$presentation",
                nodeNew(info, "ru.DmN.phtx.ppl.utils.Presentation", args)
            ))
            body += node.nodes.asSequence().drop(2)
            body += nodeGetVariable(info, "phtx\$ppl\$presentation")
            NRUseCtx.process(
                nodeUseCtx(
                    info,
                    "phtx/ppl/presentation/helper",
                    nodeBody(
                        info,
                        body
                    )
                ),
                processor,
                ctx,
                ValType.VALUE
            )
        } else null
}