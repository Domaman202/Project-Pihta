package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeDef
import ru.DmN.pht.std.ast.NodeDef.VariableOrField
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processor.utils.Variable
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.isBody
import ru.DmN.pht.std.utils.*
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.FieldModifiers
import ru.DmN.siberia.utils.VirtualField.VirtualFieldImpl
import ru.DmN.siberia.utils.VirtualType

object NRDef : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeDef {
        val list = ArrayList<VariableOrField>()
        //
        val isVariable = ctx.isBody()
        if (isVariable) {
            val bctx = ctx.body
            processor.computeList(node.nodes[0], ctx).map { processor.computeList(it, ctx) }.forEach { it ->
                lateinit var type: VirtualType
                lateinit var name: String
                val value: Node? =
                    if (it[0].isConstClass && it[1].isLiteral || it.size != 2) {
                        type = processor.computeType(it[0], ctx)
                        name = processor.computeString(it[1], ctx)
                        it.getOrNull(2)
                    } else {
                        type = processor.calc(it[1], ctx)!!
                        name = processor.computeString(it[0], ctx)
                        it[1]
                    }?.let { processor.process(it, ctx, ValType.VALUE) }
                list.add(VariableOrField.of(Variable(
                    name,
                    type,
                    value,
                    bctx.addVariable(name, type).id
                )))
            }
        } else {
            val clazz = ctx.clazz as VirtualType.VirtualTypeImpl
            processor.computeList(node.nodes[0], ctx).map { processor.computeList(it, ctx) }.forEach {
                val name = processor.computeString(it[0], ctx)
                val type = processor.computeType(it[1], ctx)
                list.add(VariableOrField.of(VirtualFieldImpl(clazz, name, type, FieldModifiers(isFinal = false, isStatic = false, isEnum = false)).apply { clazz.fields += this }))
            }
        }
        //
        return NodeDef(node.info.withType(NodeTypes.DEF_), list, isVariable)
    }
}