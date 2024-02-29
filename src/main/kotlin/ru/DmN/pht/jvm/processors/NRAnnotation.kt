package ru.DmN.pht.jvm.processors

import ru.DmN.pht.ast.NodeModifierNodesList
import ru.DmN.pht.jvm.ast.NodeAnnotation
import ru.DmN.pht.jvm.node.NodeTypes.ANN_ANN_
import ru.DmN.pht.utils.computeList
import ru.DmN.pht.utils.computeListOr
import ru.DmN.pht.utils.computeString
import ru.DmN.pht.utils.computeType
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ProcessingStage
import ru.DmN.siberia.processors.INodeProcessor
import ru.DmN.siberia.processors.NRProgn
import ru.DmN.siberia.utils.vtype.VirtualType

object NRAnnotation : INodeProcessor<NodeModifierNodesList> {
    override fun calc(node: NodeModifierNodesList, processor: Processor, ctx: ProcessingContext): VirtualType? =
        NRProgn.calc(node, processor, ctx)

    override fun process(node: NodeModifierNodesList, processor: Processor, ctx: ProcessingContext, valMode: Boolean): NodeAnnotation {
        val args = HashMap<String?, Node>()
        val nodes = ArrayList<Node>()
        val new = NodeAnnotation(
            node.info.withType(ANN_ANN_),
            nodes,
            null,
            args
        )
        //
        processor.stageManager.pushTask(ProcessingStage.METHODS_BODY) {
            processor.computeList(node.nodes[1], ctx)
                .map { it ->
                    processor.computeListOr(it, ctx)?.let {
                        if (it.size == 2)
                            Pair(processor.computeString(it[0], ctx), it[1])
                        else Pair(null, it[0])
                    } ?: Pair(null, it)
                }.forEach {
                    args[it.first] = processor.process(it.second, ctx, true)!!
                }
            //
            if (node.nodes.isNotEmpty()) {
                val list =
                    node.nodes
                        .dropLast(1) // todo: drop last for sequence
                        .asSequence()
                        .drop(2)
                        .map { processor.process(it, ctx, false) }
                        .filterNotNull()
                        .toMutableList()
                processor.process(node.nodes.last(), ctx, true)?.let { list += it }
                nodes.addAll(list) // todo: optimize
            }
            //
            new.type = processor.computeType(node.nodes[0], ctx)
        }
        //
        return new
    }
}