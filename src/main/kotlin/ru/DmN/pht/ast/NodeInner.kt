package ru.DmN.pht.ast

import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo

class NodeInner(
    info: INodeInfo,
    val field: String,
    val type: String
) : BaseNode(info) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
        indent(indent + 1).append("(field = ").append(field).append(")\n")
        indent(indent + 1).append("(type = ").append(type).append(")\n")
        indent(indent).append(']')
    }
}