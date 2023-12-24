package ru.DmN.pht.std.ast

import ru.DmN.pht.std.utils.text
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.indent

class NodeNew(info: INodeInfo, nodes: MutableList<Node>, val type: VirtualType, val ctor: VirtualMethod) : NodeNodesList(info, nodes) {
    override fun copy(): NodeNew =
        NodeNew(info, copyNodes(), type, ctor)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder = // todo: generics
        printNodes(builder.indent(indent).append('[').append(text).append('\n').indent(indent + 1).append("type = ").append(ctor.declaringClass!!.name), indent).append(']')
}