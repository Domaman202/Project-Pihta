package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.indent

class NodeTypedGet(info: INodeInfo, val name: String, val type: VirtualType) : Node(info) {
    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
            .indent(indent + 1).append("(name = ").append(name).append(")\n")
            .indent(indent + 1).append("(type = ").append(type.name).append(")\n")
            .indent(indent).append(']')
    }
}