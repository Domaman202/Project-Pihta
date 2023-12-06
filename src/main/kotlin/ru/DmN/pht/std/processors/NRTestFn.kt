package ru.DmN.pht.std.processors

import ru.DmN.pht.std.processor.utils.nodeCls
import ru.DmN.pht.std.processor.utils.nodeDefn
import ru.DmN.pht.std.processor.utils.nodeStatic
import ru.DmN.pht.std.utils.computeInt
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.*
import ru.DmN.siberia.processors.INodeProcessor
import java.lang.RuntimeException

object NRTestFn : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        when (ctx.platform) {
            Platform.UNIVERSAL -> node
            Platform.JAVA -> {
                val line = node.token.line
                NRClass.process(
                    nodeCls(
                        line,
                        "Test${processor.computeInt(node.nodes[0], ctx)}",
                        "java.lang.Object",
                        nodeStatic(
                            line,
                            nodeDefn(
                                line,
                                "test",
                                "dynamic",
                                node.nodes.drop(1).toMutableList()
                            )
                        )
                    ), processor, ctx, mode
                )
            }
        }
}