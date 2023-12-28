package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import java.util.*

class NodeMacroUnroll(info: INodeInfo, nodes: MutableList<Node>, val uuids: List<UUID>) : NodeNodesList(info, nodes) {
    override fun copy(): NodeMacroUnroll =
        NodeMacroUnroll(info, copyNodes(), uuids)

//    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
//        printNodes(builder.indent(indent).append('[').append(text).append(" (").append(uuids).append(')'), indent).append(']')
}