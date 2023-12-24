package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo

open class NodeSet(info: INodeInfo, nodes: MutableList<Node>, val name: String) : NodeNodesList(info, nodes) {
    override fun copy(): NodeSet =
        NodeSet(info, copyNodes(), name)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
//        indent(indent).append('[').append(text).append('\n').indent(indent + 1).append(name).append('\n')
////        if (value != null)
////            value!!.print(builder, indent + 1).append('\n') // todo:
//        indent(indent).append(']')
    }
}