package ru.DmN.pht.processors

import ru.DmN.pht.imports.ast.NodeAlias
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.processor.utils.PhtProcessingStage.MACROS_IMPORT
import ru.DmN.pht.processor.utils.computeList
import ru.DmN.pht.processor.utils.computeStringNodes
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.exception.pushOrRunTask

object NRAliasMacro : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeAlias? {
        val imports = processor.computeList(node.nodes[0], ctx).map { val pair = processor.computeStringNodes(it as INodesList, ctx); Pair(pair[0], pair[1]) }
        processor.pushOrRunTask(MACROS_IMPORT, node) {
            val gctx = ctx.global
            imports.forEach { gctx.macrosAliases[it.second] = gctx.getTypeName(it.first) ?: it.first }
        }
        return null
    }
}