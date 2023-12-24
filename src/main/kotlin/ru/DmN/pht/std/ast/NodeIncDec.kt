package ru.DmN.pht.std.ast

import ru.DmN.pht.std.utils.text
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.indent

class NodeIncDec(info: INodeInfo, val name: String) : Node(info) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        builder.indent(indent).append('[').append(text).append(' ').append(name).append(']')
}