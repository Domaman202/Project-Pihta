package ru.DmN.pht.std.base.processor.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.parser.ast.Node
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.processor.utils.ProcessingContext
import ru.DmN.pht.base.processor.utils.ValType
import ru.DmN.pht.std.base.processor.utils.global
import ru.DmN.pht.std.base.utils.computeList
import ru.DmN.pht.std.base.utils.computeString

object NRImport : IStdNodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
        val gctx = ctx.global
        node.nodes.map { it -> processor.computeList(it, ctx).map { processor.computeString(it, ctx) } }.forEach {
            val import = it[0]
            gctx.imports[it.getOrNull(1) ?: import.substring(import.lastIndexOf('.') + 1)] = import
        }
        return null
    }
}