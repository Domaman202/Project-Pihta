package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.indent

class NodeIncDec(info: INodeInfo, val name: String) : Node(info) {
    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder =
        builder.indent(indent).append('[').append(info.type).append('\n').indent(indent + 1).append("(name = ").append(name).append(")\n").indent(indent).append(']')
}