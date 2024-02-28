package ru.DmN.pht.ast

import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo

class NodeClassOf(info: INodeInfo, val name: String) : BaseNode(info) {
    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        builder.indent(indent).append('[').append(info.type)
        if (short)
            append(' ').append(name).append(']')
        else append('\n').indent(indent + 1).append("(class = ").append(name).append(")\n").indent(indent).append(']')
    }
}