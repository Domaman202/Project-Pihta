package ru.DmN.phtx.ppl.processors

import ru.DmN.pht.node.*
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.ValType.NO_VALUE
import ru.DmN.siberia.processor.utils.ValType.VALUE
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.processors.NRUseCtx

object NRPageSizedList : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val info = node.info
        val body = mutableListOf<Node>(
            nodeDef(
                info,
                "phtx\$ppl\$page",
                nodeNew(
                    info,
                    "ru.DmN.phtx.ppl.page.PageSizedList",
                    node.nodes.dropLast(node.nodes.size - 2).map { processor.process(it, ctx, VALUE)!! } // todo: dropLast for sequence
                )
            )
        )
        body += node.nodes.drop(2)
        body += nodeGetVariable(info, "phtx\$ppl\$page")
        return NRUseCtx.process(
            nodeUseCtx(
                info,
                "phtx/ppl/page/helper",
                nodeMCall(
                    info,
                    nodeGetVariable(info, "phtx\$ppl\$presentation"),
                    "plusAssign",
                    listOf(
                        nodeBody(
                            info,
                            body
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