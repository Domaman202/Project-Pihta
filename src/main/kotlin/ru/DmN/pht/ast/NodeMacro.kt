package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo

class NodeMacro(info: INodeInfo, nodes: MutableList<Node>, val name: String) : NodeNodesList(info, nodes) {
    override fun copy(): NodeMacro =
        NodeMacro(info, copyNodes(), name)

//    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
//        builder.indent(indent).append('[').append(text).append(' ').append(name).append(" [")
//        return printNodes(builder.append(']'), indent).append(']')
//    }
}