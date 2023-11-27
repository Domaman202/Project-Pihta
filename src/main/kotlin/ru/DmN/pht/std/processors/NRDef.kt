package ru.DmN.pht.std.processors

import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.utils.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualField.VirtualFieldImpl
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.std.ast.NodeDef
import ru.DmN.pht.std.ast.NodeDef.VariableOrField
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.isConstClass

object NRDef : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeDef {
        val gctx = ctx.global
        val list = ArrayList<VariableOrField>()
        //
        if (ctx.isBody()) {
            val bctx = ctx.body
            processor.computeList(node.nodes[0], ctx).map { processor.computeList(it, ctx) }.forEach { it ->
                lateinit var type: VirtualType
                lateinit var name: String
                val value: Node? =
                    if (it[0].isConstClass) {
                        type = gctx.getType(processor.computeString(it[0], ctx), processor.tp)
                        name = processor.computeString(it[1], ctx)
                        it.getOrNull(2)
                    } else {
                        type = processor.calc(it[1], ctx)!!
                        name = processor.computeString(it[0], ctx)
                        it[1]
                    }?.let { processor.process(it, ctx, ValType.VALUE) }
                list.add(VariableOrField.of(Variable(name, type, value, bctx.addVariable(name, type).id)))
            }
        } else {
            val clazz = ctx.clazz as VirtualType.VirtualTypeImpl
            processor.computeList(node.nodes[0], ctx).map { processor.computeList(it, ctx) }.forEach {
                val name = processor.computeString(it[0], ctx)
                val type = gctx.getType(processor.computeString(it[1], ctx), processor.tp)
                list.add(VariableOrField.of(VirtualFieldImpl(clazz, name, type, isStatic = false, isEnum = false).apply { clazz.fields += this }))
            }
        }
        //
        return NodeDef(node.token.processed(), list)
    }
}