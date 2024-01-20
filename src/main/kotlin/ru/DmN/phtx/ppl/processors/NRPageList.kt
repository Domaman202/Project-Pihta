package ru.DmN.phtx.ppl.processors

import ru.DmN.pht.std.node.*
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.ValType.NO_VALUE
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.processors.NRUseCtx

object NRPageList : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val info = node.info
        return NRUseCtx.process(
            nodeUseCtx(
                info,
                "phtx/ppl/page/helper",
                nodeMCall(
                    info,
                    nodeGetVariable(info, "phtx\$ppl\$presentation"),
                    "plusAssign",
                    listOf(
                        nodeNew(
                            info,
                            "ru.DmN.phtx.ppl.page.PageList",
                            mutableListOf(
                                nodeListOf(
                                    info,
                                    node.nodes
                                )
                            )
                        )
                    )
                )
            ),
            processor,
            ctx,
            NO_VALUE
        )
    }
}