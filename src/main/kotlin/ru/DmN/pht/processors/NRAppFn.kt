package ru.DmN.pht.processors

import ru.DmN.pht.processor.ctx.clazzOrNull
import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.pht.utils.node.nodeCls
import ru.DmN.pht.utils.node.nodeDefn
import ru.DmN.pht.utils.node.nodeStatic
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor

object NRAppFn : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val info = node.info
        val fn = nodeStatic(
            info,
            nodeDefn(
                info,
                "main",
                "void",
                if (ctx.platform == JVM) listOf(Pair("args", "[String")) else emptyList(),
                node.nodes
            )
        )
        return if (ctx.clazzOrNull?.name == "App")
            processor.process(fn, ctx, valMode)
        else processor.process(nodeCls(info, "App", "Object", fn), ctx, valMode)
    }
}