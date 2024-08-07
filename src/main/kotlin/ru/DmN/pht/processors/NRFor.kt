package ru.DmN.pht.processors

import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.utils.computeList
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.pht.utils.node.*
import ru.DmN.pht.utils.node.NodeParsedTypes.LESS
import ru.DmN.pht.utils.node.NodeTypes.INC_PRE
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.Variable

object NRFor : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        when (ctx.platform) {
            JVM -> {
                val info = node.info
                val nodes = processor.computeList(node.nodes[0], ctx)
                val name = processor.computeString(nodes[0], ctx)
                val type = processor.calc(nodes[1], ctx)!!
                if (type.componentType == null) {
                    val iter = Variable.tmp(node, 1)
                    val code = ArrayList<Node>()
                    code += if (type.isAssignableFrom(ctx.global.getType("Iterable")))
                        nodeDef(info, iter, nodeMCall(info, nodes[1], "iterator", listOf()))
                    else nodeDef(info, iter, nodes[1])
                    code += nodeCycle(
                        info,
                        nodeMCall(info, nodeGetOrName(info, iter), "hasNext", listOf()),
                        listOf(
                            if (name == "_")
                                nodeMCall(info, nodeGetOrName(info, iter), "next", emptyList())
                            else nodeDef(info, name, nodeMCall(info, nodeGetOrName(info, iter), "next", emptyList()))
                        ) + node.nodes.drop(1)
                    )
                    processor.process(nodeBody(info, code), ctx, valMode)
                } else {
                    val arr = Variable.tmp(node, 1)
                    val i = Variable.tmp(node, 2)
                    processor.process(
                        nodeBody(
                            info,
                            mutableListOf(
                                nodeDef(info, arr, nodes[1]),
                                nodeDef(info, i, nodeValue(info, 0)),
                                nodeCycle(
                                    info,
                                    NodeNodesList(
                                        info.withType(LESS),
                                        mutableListOf(
                                            nodeGetOrName(info, i),
                                            nodeArraySize(info, arr)
                                        )
                                    ),
                                    if (name != "_")
                                        listOf(nodeDef(info, name, nodeAGet(info, arr, i)))
                                            + node.nodes.drop(1)
                                            + NodeNodesList(info.withType(INC_PRE), mutableListOf(nodeGetOrName(info, i)))
                                    else node.nodes.drop(1)
                                            + NodeNodesList(info.withType(INC_PRE), mutableListOf(nodeGetOrName(info, i)))
                                )
                            )
                        ),
                        ctx,
                        valMode
                    )
                }
            }

            else -> node
        }
}