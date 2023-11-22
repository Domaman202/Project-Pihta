package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.processor.utils.Platform
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.processor.utils.platform
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString

object NRFor : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? =
        if (ctx.platform == Platform.JAVA) {
            val nodes = processor.computeList(node.nodes[0], ctx)
            val type = processor.calc(nodes[1], ctx)!!
            if (type.componentType == null) {
                val iter = Variable.tmp(node, 1)
                val name = processor.computeString(nodes[0], ctx)
                val line = node.token.line
                val code = ArrayList<Node>()
                code += if (type.isAssignableFrom(ctx.global.getType("Iterable", processor.tp)))
                    nodeDef(line, iter, nodeMCall(line, nodes[1], "iterator", listOf()))
                else nodeDef(line, iter, nodes[1])
                code += nodeCycle(
                    line,
                    nodeMCall(line, nodeGetOrName(line, iter), "hasNext", listOf()),
                    listOf(
                        nodeDef(
                            line,
                            name,
                            nodeMCall(
                                line,
                                nodeGetOrName(line, iter),
                                "next",
                                emptyList()
                            )
                        )
                    ) + node.nodes.drop(1)
                )
                NRBody.process(nodeBody(line, code), processor, ctx, mode)
            } else {
                val arr = Variable.tmp(node, 1)
                val i = Variable.tmp(node, 2)
                val name = processor.computeString(nodes[0], ctx)
                val line = node.token.line
                NRBody.process(
                    nodeBody(
                        line,
                        mutableListOf(
                            nodeDef(line, arr, nodes[1]),
                            nodeDef(line, i, nodeValueOf(line, 0)),
                            nodeCycle(
                                line,
                                NodeNodesList(
                                    Token.operation(line, "less"),
                                    mutableListOf(
                                        nodeGetOrName(line, i),
                                        nodeArraySize(line, arr)
                                    )
                                ),
                                listOf(nodeDef(line, name, nodeAGet(line, arr, i))) +
                                        node.nodes.drop(1) +
                                        NodeNodesList(
                                            Token.operation(line, "inc"),
                                            mutableListOf(nodeValueOf(line, i))
                                        )
                            )
                        )
                    ), processor, ctx, mode
                )
            }
        } else node
}