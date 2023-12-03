package ru.DmN.pht.std.processors

import ru.DmN.pht.std.processor.utils.nodeCls
import ru.DmN.pht.std.processor.utils.nodeStatic
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.Platform
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor

object NRApp : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        when (ctx.platform) {
            Platform.UNIVERSAL -> node
            Platform.JAVA -> {
                val line = node.token.line
                NRClass.process(
                    nodeCls(
                        line,
                        "App",
                        "java.lang.Object",
                        nodeStatic(line, node.nodes)
                    ), processor, ctx, mode
                )
            }
        }
}