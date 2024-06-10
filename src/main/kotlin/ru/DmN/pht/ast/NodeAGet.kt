package ru.DmN.pht.ast

import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo

class NodeAGet(
    info: INodeInfo,
    val arr: Node,
    val index: Node
) : BaseNode(info) {
    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
            .indent(indent + 1).append("(array:\n")
        arr.print(builder, indent + 2, short).append('\n').indent(indent + 1).append(")\n")
            .indent(indent + 1).append("(index:\n")
        index.print(builder, indent + 2, short).append('\n').indent(indent + 1).append(")\n")
            .indent(indent).append(']')
    }
}