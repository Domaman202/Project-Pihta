package ru.DmN.pht.processors

import ru.DmN.pht.utils.Platforms.CPP
import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.pht.utils.computeString
import ru.DmN.pht.utils.node.*
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor

object NRTestFn : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        when (ctx.platform) {
            JVM -> {
                val info = node.info
                processor.process(
                    nodeCls(
                        info,
                        "Test${processor.computeString(node.nodes[0], ctx)}",
                        "Object",
                        nodeStatic(
                            info,
                            nodeDefn(
                                info,
                                "test",
                                "dynamic",
                                node.nodes.drop(1).toMutableList()
                            )
                        )
                    ),
                    ctx,
                    false
                )
            }

            CPP -> {
                val info = node.info
                processor.process(
                    nodeTest(
                        info,
                        nodeCls(
                            info,
                            "Test${processor.computeString(node.nodes[0], ctx)}",
                            "Object",
                            nodeStatic(
                                info,
                                nodeDefn(
                                    info,
                                    "test",
                                    "void",
                                    nodePrintln(
                                        info,
                                        node.nodes.drop(1).toMutableList()
                                    )
                                )
                            )
                        )
                    ),
                    ctx,
                    false
                )!!
            }

            else -> node
        }
}