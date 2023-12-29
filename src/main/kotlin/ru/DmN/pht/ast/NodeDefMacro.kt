package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import java.util.*

class NodeDefMacro(info: INodeInfo, nodes: MutableList<Node>, val uuid: UUID) : NodeNodesList(info, nodes) {
    override fun copy(): NodeDefMacro =
        NodeDefMacro(info, copyNodes(), uuid)

//    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
//        printNodes(builder.indent(indent).append('[').append(text).append(" (").append(uuid).append(')'), indent).append(']')
}