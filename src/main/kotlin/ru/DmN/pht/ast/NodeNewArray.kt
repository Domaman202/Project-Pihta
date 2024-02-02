package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.indent

class NodeNewArray(info: INodeInfo, val type: VirtualType, val size: Node) : Node(info) {
    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
            .indent(indent + 1).append("(type = ").append(type.name).append(")\n")
            .indent(indent + 1).append("(size:\n")
        size.print(builder, indent + 2, short).append('\n').indent(indent + 1).append(")\n")
            .indent(indent).append(']')
    }
}