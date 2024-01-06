package ru.DmN.pht.std.processors

import ru.DmN.pht.std.imports.ast.NodeAliasType
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeStringNodes
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.*
import ru.DmN.siberia.processors.INodeProcessor

object NRAliasType : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeAliasType? {
        val imports = processor.computeList(node.nodes[0], ctx).map { val pair = processor.computeStringNodes(it as INodesList, ctx); Pair(pair[0], pair[1]) }
        processor.stageManager.pushTask(ProcessingStage.TYPES_IMPORT) {
            val gctx = ctx.global
            imports.forEach {
                gctx.aliases[it.second] = gctx.getTypeName(it.first) ?: it.first
            }
        }
        return when (ctx.platform) {
            Platforms.JAVA -> null
            else -> NodeAliasType(node.info.withType(NodeTypes.ALIAS_TYPE_), imports)
        }
    }
}