package ru.DmN.pht.std.processors

import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.NodeNodesList
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.base.processors.INodeProcessor
import ru.DmN.pht.base.utils.VirtualField
import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.ast.NodeFGet
import ru.DmN.pht.std.utils.computeString
import ru.DmN.pht.std.utils.processNodes

object NRFGetA : INodeProcessor<NodeNodesList> {
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        findField(processor.calc(node.nodes[0], ctx), processor.computeString(node.nodes[1], ctx), node.nodes[0].isConstClass())?.type

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, mode: ValType): NodeFGet? {
        return if (mode == ValType.VALUE) {
            val nodes = processor.processNodes(node, ctx, ValType.VALUE)
            val name = processor.computeString(nodes[1], ctx)
            val type = processor.calc(nodes[0], ctx)!!
            NodeFGet(
                node.token.processed(),
                mutableListOf(nodes[0]),
                name,
                type.let {
                    val field = findField(it, name, nodes[0].isConstClass())
                    if (field == null)
                        NodeFGet.Type.UNKNOWN
                    else if (field.static)
                        NodeFGet.Type.STATIC
                    else NodeFGet.Type.INSTANCE
                },
                type
            )
        } else null
    }

    fun findField(instance: VirtualType?, name: String, static: Boolean): VirtualField? =
        instance?.fields?.find { it.name == name && it.static == static }
}