package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.indent

class NodeClassOf(info: INodeInfo, val name: String) : Node(info) {
    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        builder.indent(indent).append('[').append(info.type)
        if (short)
            append(' ').append(name).append(']')
        else append('\n').indent(indent + 1).append("(class = ").append(name).append(")\n").indent(indent).append(']')
    }
}