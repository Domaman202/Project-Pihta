package ru.DmN.pht.std.processors

import ru.DmN.pht.std.node.nodeCls
import ru.DmN.pht.std.node.nodeDefn
import ru.DmN.pht.std.node.nodeStatic
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.Platforms
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor

object NRTestFn : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        when (ctx.platform) {
            Platforms.JAVA -> {
                val info = node.info
                NRClass.process(
                    nodeCls(
                        info,
                        "Test${processor.computeString(node.nodes[0], ctx)}",
                        "java.lang.Object",
                        nodeStatic(
                            info,
                            nodeDefn(
                                info,
                                "test",
                                "dynamic",
                                node.nodes.drop(1).toMutableList()
                            )
                        )
                    ), processor, ctx, mode
                )
            }

            else -> node
        }
}