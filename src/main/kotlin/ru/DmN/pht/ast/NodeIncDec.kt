package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo

class NodeIncDec(info: INodeInfo, val name: String) : Node(info) {
//    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
//        builder.indent(indent).append('[').append(text).append(' ').append(name).append(']')
}