package ru.DmN.phtx.pcl.ast

import ru.DmN.siberia.lexer.Token

class NodeArray(token: Token, offset: Int, name: String, val nodes: Array<NodeElement>) : NodeElement(token, offset, name), INodeArray {
    override val size: Int
        get() = nodes.size

    override fun get(index: Int): NodeElement =
        nodes[index]

    override fun forEach(consumer: (it: NodeElement) -> Unit) {
        nodes.forEach(consumer)
    }

    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        super.print(builder, indent)
        nodes.forEach { it.print(builder.append('\n'), indent + 1) }
        return builder
    }
}