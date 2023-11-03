package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualField
import ru.DmN.pht.std.ast.NodeField
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString

object NRField : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): Node {
        val gctx = ctx.global
        val clazz = ctx.clazz
        val list = ArrayList<VirtualField>()
        processor.computeList(node.nodes[0], ctx).map { it -> processor.computeList(it, ctx).map { processor.computeString(it, ctx) } }.forEach {
            VirtualField(clazz, it[0], gctx.getType(it[1], processor.tp), static = false, enum = false).run {
                list += this
                clazz.fields += this
            }
        }
        return NodeField(node.token.processed(), list)
    }
}