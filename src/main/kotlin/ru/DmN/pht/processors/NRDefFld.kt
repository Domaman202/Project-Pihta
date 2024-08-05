package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeFieldB
import ru.DmN.pht.processor.ctx.clazz
import ru.DmN.pht.processor.utils.computeList
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.processor.utils.computeType
import ru.DmN.pht.utils.node.NodeTypes.DEF_FLD_
import ru.DmN.pht.utils.vtype.PhtVirtualType
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.FieldModifiers
import ru.DmN.siberia.utils.vtype.VirtualField.VirtualFieldImpl

object NRDefFld : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? {
        val list = ArrayList<VirtualFieldImpl>()
        val clazz = ctx.clazz as PhtVirtualType.Impl
        processor.computeList(node.nodes[0], ctx).map { processor.computeList(it, ctx) }.forEach {
            val name = processor.computeString(it[0], ctx)
            val type = processor.computeType(it[1], ctx)
            list.add(
                VirtualFieldImpl(
                    clazz,
                    name,
                    type,
                    FieldModifiers(isFinal = false, isStatic = false, isEnum = false)
                ).apply { clazz.fields += this }
            )
        }
        return NodeFieldB(node.info.withType(DEF_FLD_), list)
    }
}