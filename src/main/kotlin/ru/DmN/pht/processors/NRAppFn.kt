package ru.DmN.pht.std.processors

import ru.DmN.pht.std.node.nodeCls
import ru.DmN.pht.std.node.nodeDefn
import ru.DmN.pht.std.node.nodeStatic
import ru.DmN.pht.std.processor.utils.clazzOrNull
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.Platforms.JVM
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor

object NRAppFn : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node =
        when (ctx.platform) {
            JVM -> {
                val info = node.info
                val fn = nodeStatic(info, nodeDefn(info, "main", "void", node.nodes))
                if (ctx.clazzOrNull?.name == "App")
                    processor.process(fn, ctx, mode)!!
                else NRClass.process(nodeCls(info, "App", "java.lang.Object", fn), processor, ctx, mode)
            }

            else -> node
        }
}