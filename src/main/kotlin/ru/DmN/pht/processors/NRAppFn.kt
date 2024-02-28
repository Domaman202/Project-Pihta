package ru.DmN.pht.processors

import ru.DmN.pht.processor.utils.clazzOrNull
import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.pht.utils.node.nodeCls
import ru.DmN.pht.utils.node.nodeDefn
import ru.DmN.pht.utils.node.nodeStatic
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor

object NRAppFn : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node =
        when (ctx.platform) {
            JVM -> {
                val info = node.info
                val fn = nodeStatic(info, nodeDefn(info, "main", "void", node.nodes))
                if (ctx.clazzOrNull?.name == "App")
                    processor.process(fn, ctx, valMode)!!
                else NRClass.process(nodeCls(info, "App", "java.lang.Object", fn), processor, ctx, valMode)
            }

            else -> node
        }
}