package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeDef
import ru.DmN.pht.ast.NodeDef.VariableOrField
import ru.DmN.pht.processor.ctx.body
import ru.DmN.pht.processor.ctx.isBody
import ru.DmN.pht.processor.utils.Variable
import ru.DmN.pht.processor.utils.computeList
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.processor.utils.computeType
import ru.DmN.pht.utils.isConstClass
import ru.DmN.pht.utils.isLiteral
import ru.DmN.pht.utils.node.NodeTypes.DEF_
import ru.DmN.pht.utils.node.NodeTypes.DEF_FLD
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualType

object NRDef : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node? =
        if (ctx.isBody()) {
            val list = ArrayList<VariableOrField>()
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
                    }?.let { processor.process(it, ctx, true) }
                list.add(
                    VariableOrField.of(
                        Variable(
                            name,
                            type,
                            value,
                            bctx.addVariable(name, type).id
                        )
                    )
                )
            }
            NodeDef(node.info.withType(DEF_), list, true)
        } else processor.process(NodeNodesList(node.info.withType(DEF_FLD), node.nodes), ctx, valMode)
}