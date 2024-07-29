package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeFGet
import ru.DmN.pht.ast.NodeFGet.Type.*
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.processor.utils.computeType
import ru.DmN.pht.utils.isConstClass
import ru.DmN.pht.utils.node.NodeParsedTypes.FSET_A
import ru.DmN.pht.utils.node.NodeTypes
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.utils.vtype.VirtualField
import ru.DmN.siberia.utils.vtype.VirtualType

object NRFGetA :
    INodeProcessor<NodeNodesList>,
    ISetterGetterProcessor<NodeNodesList>
{
    override fun calc(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? {
        val static = node.nodes[0].isConstClass
        return findField(
            if (static)
                processor.computeType(node.nodes[0], ctx)
            else processor.calc(node.nodes[0], ctx),
            processor.computeString(node.nodes[1], ctx),
            static
        )?.type
    }

    override fun process(node: NodeNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeFGet? =
        if (valMode)
            processAsGetter(node, processor, ctx)
        else null

    override fun processAsSetterLazy(node: NodeNodesList, values: List<Node>, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeNodesList =
        NodeNodesList(node.info.withType(FSET_A), node.nodes.apply { addAll(values) })

    override fun processAsSetter(node: NodeNodesList, values: List<Node>, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node =
        NRFSetA.process(node.apply { nodes.addAll(values) }, processor, ctx, valMode)

    override fun processAsGetter(node: NodeNodesList, processor: Processor, ctx: ProcessingContext): NodeFGet {
        val instance = processor.process(node.nodes[0], ctx, true)!!
        val name = processor.computeString(node.nodes[1], ctx)
        val type =
            if (instance.isConstClass)
                processor.computeType(node.nodes[0], ctx)
            else processor.calc(instance, ctx)!!
        return NodeFGet(
            node.info.withType(NodeTypes.FGET_),
            mutableListOf(instance),
            name,
            type.let {
                val field = findField(it, name, instance.isConstClass)
                if (field == null)
                    UNKNOWN
                else if (field.modifiers.isStatic)
                    STATIC
                else INSTANCE
            },
            type
        )
    }

    fun findField(instance: VirtualType?, name: String, static: Boolean): VirtualField? =
        instance?.fields?.find { it.name == name && it.modifiers.isStatic == static }
}