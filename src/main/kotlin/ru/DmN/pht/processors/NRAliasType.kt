package ru.DmN.pht.processors

import ru.DmN.pht.helper.ast.NodeAliasType
import ru.DmN.pht.processor.ctx.global
import ru.DmN.pht.utils.Platforms.CPP
import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.pht.utils.computeList
import ru.DmN.pht.utils.computeStringNodes
import ru.DmN.pht.utils.node.NodeTypes.ALIAS_TYPE_
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage.TYPES_IMPORT
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.INodeProcessor

object NRAliasType : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeAliasType? {
        val imports = processor.computeList(node.nodes[0], ctx).map { val pair = processor.computeStringNodes(it as INodesList, ctx); Pair(pair[0], pair[1]) }
        processor.stageManager.pushTask(TYPES_IMPORT) {
            val gctx = ctx.global
            imports.forEach {
                gctx.aliases[it.second] = gctx.getTypeName(it.first) ?: it.first
            }
        }
        return when (ctx.platform) {
            JVM, CPP -> null
            else -> NodeAliasType(node.info.withType(ALIAS_TYPE_), imports)
        }
    }
}