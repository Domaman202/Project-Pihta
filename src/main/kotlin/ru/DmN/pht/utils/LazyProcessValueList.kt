package ru.DmN.pht.std.utils

import ru.DmN.siberia.Processor
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType

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
            process.process(node.nodes[i], ctx, ValType.VALUE)!!.apply { node.nodes[i] = this }
        }

    fun dropAndProcess(offset: Int): MutableList<Node> {
        val list = ArrayList<Node>()
        for (i in offset until node.nodes.size)
            list += get(i)
        return list
    }

    fun drop(offset: Int): MutableList<Node> {
        val list = ArrayList<Node>()
        for (i in offset until node.nodes.size)
            list += node.nodes[i]
        return list
    }
}