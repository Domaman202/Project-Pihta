package ru.DmN.pht.std.processors

import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.pht.std.imports.ast.NodeAliasType
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.computeStringNodes

object NRAliasType : INodeProcessor<NodeNodesList> { // todo: remake to multi alias
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeAliasType {
        val nodes = processor.computeStringNodes(node, ctx)
        val type = nodes[0]
        val new = nodes[1]
        processor.stageManager.pushTask(ProcessingStage.TYPES_IMPORT) {
            val gctx = ctx.global
            gctx.imports[new] = gctx.getTypeName(type) ?: type
        }
        return NodeAliasType(node.token.processed(), type, new)
    }
}