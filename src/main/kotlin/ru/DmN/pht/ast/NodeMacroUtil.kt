package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.node.INodeInfo
import java.util.*

class NodeMacroUtil(
    info: INodeInfo,
    nodes: MutableList<Node>,
    val uuids: List<UUID>
) : NodeNodesList(info, nodes) {
    override fun copy(): NodeMacroUtil =
        NodeMacroUtil(info, copyNodes(), uuids)

//    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
//        builder.indent(indent).append('[').append(text).append(" (").append(uuids).append(')')
//        return printNodes(builder, indent).append(']')
//    }
}