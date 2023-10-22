package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.lexer.Token
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeDef
import ru.DmN.pht.std.processor.utils.Variable
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.line

object NRDef : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeDef {
        val gctx = ctx.global
        val bctx = ctx.body
        //
        val list = ArrayList<Variable>()
        processor.computeList(node.nodes[0], ctx).map { processor.computeList(it, ctx) }.forEach { it ->
            lateinit var type: VirtualType
            lateinit var name: String
            val value: Node? =
                if (it[0].isConstClass()) {
                    type = gctx.getType(processor.computeString(it[0], ctx), processor.tp)
                    name = processor.computeString(it[1], ctx)
                    it.getOrNull(2)
                } else {
                    type = processor.calc(it[1], ctx)!!
                    name = processor.computeString(it[0], ctx)
                    it[1]
                }?.let { processor.process(it, ctx, ValType.VALUE) }
            list.add(Variable(name, type, value, bctx.addVariable(name, type).id))
        }
        return NodeDef(node.token.processed(), list)
    }
}