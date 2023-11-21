package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.processor.utils.ProcessingStage
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.processNodes

object NRImportExtends : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        processor.pushTask(ctx, ProcessingStage.EXTENDS_IMPORT) {
            val gctx = ctx.global
            processor.processNodes(node.nodes[0], ctx, ValType.VALUE).map { processor.computeString(it, ctx) }.forEach { it ->
                gctx.getType(it, processor.tp).methods.filter { it.extend != null }.forEach {
                    gctx.getExtends(it.extend!!) += it
                }
            }
        }
        return null
    }
}