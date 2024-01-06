package ru.DmN.pht.std.processors

import ru.DmN.pht.std.node.NodeParsedTypes
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.Platforms
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.Variable

object NRFor : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        when (ctx.platform) {
            Platforms.JAVA -> {
                val info = node.info
                val nodes = processor.computeList(node.nodes[0], ctx)
                val name = processor.computeString(nodes[0], ctx)
                val type = processor.calc(nodes[1], ctx)!!
                if (type.componentType == null) {
                    val iter = Variable.tmp(node, 1)
                    val code = ArrayList<Node>()
                    code += if (type.isAssignableFrom(ctx.global.getType("Iterable", processor.tp)))
                        nodeDef(info, iter, nodeMCall(info, nodes[1], "iterator", listOf()))
                    else nodeDef(info, iter, nodes[1])
                    code += nodeCycle(
                        info,
                        nodeMCall(info, nodeGetOrName(info, iter), "hasNext", listOf()),
                        listOf(
                            nodeDef(
                                info,
                                name,
                                nodeMCall(
                                    info,
                                    nodeGetOrName(info, iter),
                                    "next",
                                    emptyList()
                                )
                            )
                        ) + node.nodes.drop(1)
                    )
                    NRBody.process(nodeBody(info, code), processor, ctx, mode)
                } else {
                    val arr = Variable.tmp(node, 1)
                    val i = Variable.tmp(node, 2)
                    NRBody.process(
                        nodeBody(
                            info,
                            mutableListOf(
                                nodeDef(info, arr, nodes[1]),
                                nodeDef(info, i, nodeValue(info, 0)),
                                nodeCycle(
                                    info,
                                    NodeNodesList(
                                        info.withType(NodeParsedTypes.LESS),
                                        mutableListOf(
                                            nodeGetOrName(info, i),
                                            nodeArraySize(info, arr)
                                        )
                                    ),
                                    listOf(nodeDef(info, name, nodeAGet(info, arr, i))) +
                                            node.nodes.drop(1) +
                                            NodeNodesList(
                                                info.withType(NodeParsedTypes.INC_PRE),
                                                mutableListOf(nodeValue(info, i))
                                            )
                                )
                            )
                        ), processor, ctx, mode
                    )
                }
            }

            else -> node
        }
}