package ru.DmN.pht.std.processors

import ru.DmN.pht.std.ast.NodeMacroUtil
import ru.DmN.pht.std.ast.NodeModifierNodesList
import ru.DmN.pht.std.node.NodeTypes.PROGN_B_
import ru.DmN.pht.std.processor.utils.macro
import ru.DmN.pht.std.processor.utils.with
import ru.DmN.pht.std.utils.computeList
import ru.DmN.pht.std.utils.computeString
import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processors.INodeProcessor
import java.util.*
import kotlin.math.min

object NRMacroUnroll : INodeProcessor<NodeMacroUtil> { // todo: calc
    override fun process(node: NodeMacroUtil, processor: Processor, ctx: ProcessingContext, mode: ValType): Node? {
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
                    nodes += processor.process(it, context, mode)!!
                }
            }
        }
        return NodeModifierNodesList(info.withType(PROGN_B_), nodes)
    }
}