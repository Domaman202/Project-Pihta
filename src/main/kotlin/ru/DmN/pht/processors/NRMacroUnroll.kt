package ru.DmN.pht.processors

import ru.DmN.pht.ast.NodeMacroUtil
import ru.DmN.pht.ast.NodeMetaNodesList
import ru.DmN.pht.processor.ctx.macro
import ru.DmN.pht.processor.ctx.with
import ru.DmN.pht.processor.utils.computeList
import ru.DmN.pht.processor.utils.computeString
import ru.DmN.pht.utils.node.NodeTypes.PROGN_B_
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processors.INodeProcessor
import java.util.*
import kotlin.math.min

object NRMacroUnroll : INodeProcessor<NodeMacroUtil> { // todo: calc
    override fun process(node: NodeMacroUtil, processor: Processor, ctx: ProcessingContext, valMode: Boolean): Node {
        val nodes = ArrayList<Node>()
        var maxIndex = Int.MAX_VALUE
        val names = ArrayList<Triple<List<Node>, String, UUID>>()
        val macro = ctx.macro
        processor.computeList(node.nodes[0], ctx)
            .stream()
            .map { it -> processor.computeList(it, ctx).map { processor.computeString(it, ctx) } }
            .forEach {
                names += Triple(
                    processor.computeList(NRMacroArg.findMacroArgument(macro, node.uuids, it[1]), ctx).apply { maxIndex = min(maxIndex, size) },
                    it[0],
                    node.uuids.first()
                )
            }
        val info = node.info
        if (maxIndex != Int.MAX_VALUE) {
            for (i in 0 until maxIndex) {
                val args = HashMap<Pair<UUID, String>, Node>()
                names.forEach {
                    args[Pair(it.third, it.second)] = it.first[i]
                }
                val context = ctx.with(NRMacro.macroCtxOf(ctx, args))
                node.nodes.stream().skip(1).forEach {
                    nodes += processor.process(it, context, valMode)!!
                }
            }
        }
        return NodeMetaNodesList(info.withType(PROGN_B_), nodes)
    }
}