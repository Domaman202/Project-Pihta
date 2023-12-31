package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeFGet
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.computeType
import ru.DmN.pht.std.utils.isConstClass
import ru.DmN.pht.std.utils.processNodes
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.VirtualField
import ru.DmN.siberia.utils.VirtualType

object NRFGetA : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        findField(processor.calc(node.nodes[0], ctx), processor.computeString(node.nodes[1], ctx), node.nodes[0].isConstClass)?.type

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeFGet? {
        return if (mode == ValType.VALUE) {
            val nodes = processor.processNodes(node, ctx, ValType.VALUE)
            val name = processor.computeString(nodes[1], ctx)
            val type =
                if (nodes[0].isConstClass)
                    processor.computeType(node.nodes[0], ctx)
                else processor.calc(nodes[0], ctx)!!
            NodeFGet(
                node.info.withType(NodeTypes.FGET_),
                mutableListOf(nodes[0]),
                name,
                type.let {
                    val field = findField(it, name, nodes[0].isConstClass)
                    if (field == null)
                        NodeFGet.Type.UNKNOWN
                    else if (field.modifiers.isStatic)
                        NodeFGet.Type.STATIC
                    else NodeFGet.Type.INSTANCE
                },
                type
            )
        } else null
    }

    fun findField(instance: VirtualType?, name: String, static: Boolean): VirtualField? =
        instance?.fields?.find { it.name == name && it.modifiers.isStatic == static }
}