package ru.DmN.phtx.pcl.ast

import ru.DmN.phtx.pcl.laxer.Laxer
import ru.DmN.siberia.lexer.Token

class NodeLazyMap(token: Token, offset: Int, laxer: Laxer, size: Int) : NodeLazyElement(token, offset, laxer), INodeArray {
    val nodes: Array<NodeElement?> = arrayOfNulls(size)

    override val size: Int
        get() = nodes.size

    override operator fun get(index: Int): NodeElement =
        if (nodes[index] == null) {
            val node = laxer.parse(token.line + index + 1)
            if (node.offset - 1 == offset) {
                nodes[index] = node
                node
            } else throw RuntimeException()
        } else nodes[index]!!

    override fun forEach(consumer: (it: NodeElement) -> Unit) {
        for (i in nodes.indices) {
            consumer(get(i))
        }
    }

    override fun printWLAF(builder: StringBuilder, indent: Int): StringBuilder {
        for (i in nodes.indices)
            get(i).print(builder, indent + 1).append('\n')
        return builder
    }
}