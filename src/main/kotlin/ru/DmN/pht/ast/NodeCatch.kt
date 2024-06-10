package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType

class NodeCatch(
    info: INodeInfo,
    nodes: MutableList<Node>,
    var type: VirtualType?,
    val catchers: List<Triple<String, VirtualType, Node?>>
) : NodeNodesList(info, nodes) {
    override fun copy(): NodeCatch =
        NodeCatch(info, copyNodes(), type, catchers)

    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
            .indent(indent + 1).append("(catchers:")
        if (catchers.isNotEmpty()) {
            catchers.forEach {
                append('\n').indent(indent + 2).append('[').append('\n')
                    .indent(indent + 3).append("(var = ").append(it.first).append(")\n")
                    .indent(indent + 3).append("(exception = ").append(it.second.name).append(")\n")
                it.third?.print(builder, indent + 3, short)?.append('\n')
                indent(indent + 2).append(']')
            }
            append('\n').indent(indent + 1)
        }
        append(')')
        printNodes(builder, indent, short).append(']')
    }
}