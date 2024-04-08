package ru.DmN.pht.utils

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext

class LazyProcessValueList(
    val node: NodeNodesList,
    val process: Processor,
    val ctx: ProcessingContext
) {
    val processed: MutableList<Int> = ArrayList(node.nodes.size)

    operator fun get(i: Int): Node =
        if (processed.contains(i))
            node.nodes[i]
        else {
            processed += i
            process.process(node.nodes[i], ctx, true)!!.apply { node.nodes[i] = this }
        }

    fun drop(offset: Int): MutableList<Node> {
        val list = ArrayList<Node>()
        for (i in offset until node.nodes.size)
            list += node.nodes[i]
        return list
    }
}