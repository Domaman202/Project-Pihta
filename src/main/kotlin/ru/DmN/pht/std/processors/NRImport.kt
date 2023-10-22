package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString

object NRImport : ru.DmN.pht.std.processors.IStdNodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        val gctx = ctx.global
        node.nodes.map { it -> processor.computeList(it, ctx).map { processor.computeString(it, ctx) } }.forEach {
            val import = it[0]
            gctx.imports[it.getOrNull(1) ?: import.substring(import.lastIndexOf('.') + 1)] = import
        }
        return null
    }
}