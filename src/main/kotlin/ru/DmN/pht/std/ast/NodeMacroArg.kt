package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import java.util.*

class NodeMacroArg(info: INodeInfo, nodes: MutableList<Node>, val uuids: List<UUID>) : NodeNodesList(info, nodes) {
    override fun copy(): NodeMacroArg =
        NodeMacroArg(info, copyNodes(), uuids)

//    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
//        builder.indent(indent).append('[').append(text).append(" (").append(uuids).append(')')
//        return printNodes(builder, indent).append(']')
//    }
}