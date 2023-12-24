package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeGetA
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.processor.utils.body
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.isBody
import ru.DmN.pht.std.processor.utils.isClass
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor

object NRGetB : INodeProcessor<NodeNodesList> {
    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeGetA {
        val name = processor.computeString(processor.process(node.nodes[0], ctx, mode)!!, ctx)
        var type = NodeGetA.Type.UNKNOWN
        if (ctx.isBody() && ctx.body[name] != null)
            type = NodeGetA.Type.VARIABLE
        else if (ctx.isClass() && ctx.clazz.fields.find { it.name == name } != null)
            type = NodeGetA.Type.THIS_FIELD
        return NodeGetA(node.info.withType(NodeTypes.GET_), name, type)
    }
}