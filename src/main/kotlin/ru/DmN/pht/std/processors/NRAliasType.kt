package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ProcessingStage
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.std.imports.ast.NodeAliasType
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.computeStringNodes

object NRAliasType : INodeProcessor<NodeNodesList> { // todo: remake to multi alias
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeAliasType {
        val nodes = processor.computeStringNodes(node, ctx)
        val type = nodes[0]
        val new = nodes[1]
        processor.pushTask(ctx, ProcessingStage.TYPES_IMPORT) {
            val gctx = ctx.global
            gctx.imports[new] = gctx.getTypeName(type) ?: type
        }
        return NodeAliasType(node.token.processed(), type, new)
    }
}