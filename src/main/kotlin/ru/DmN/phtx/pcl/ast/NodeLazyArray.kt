package ru.DmN.phtx.pcl.ast

import ru.DmN.phtx.pcl.laxer.Laxer
import ru.DmN.siberia.lexer.Token

class NodeLazyArray(token: Token, offset: Int, name: String, laxer: Laxer, size: Int) : NodeLazyElement(token, offset, name, laxer), INodeArray {
    val nodes: Array<NodeElement?> = arrayOfNulls(size)

    override operator fun get(index: Int): NodeElement =
        if (nodes[index] == null) {
            val node = laxer.parse(token.line + index + 1)
            if (node.offset - 1 == offset) {
                nodes[index] = node
                node
            } else throw RuntimeException()
        } else nodes[index]!!

    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        super.print(builder, indent)
        for (i in nodes.indices)
            get(i).print(builder.append('\n'), indent + 1)
        return builder
    }
}