package ru.DmN.pht.ast

import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo

class NodeIncDec(info: INodeInfo, val name: String) : BaseNode(info) {
    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder =
        builder.indent(indent).append('[').append(info.type).append('\n').indent(indent + 1).append("(name = ").append(name).append(")\n").indent(indent).append(']')
}