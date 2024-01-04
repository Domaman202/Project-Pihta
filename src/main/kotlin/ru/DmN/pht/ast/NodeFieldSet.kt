package ru.DmN.pht.std.ast

import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo

class NodeFieldSet(info: INodeInfo, nodes: MutableList<Node>, val instance: Node, name: String, val static: Boolean, val native: Boolean = false) : NodeSet(info, nodes, name) {
    override fun copy(): NodeFieldSet =
        NodeFieldSet(info, copyNodes(), instance, name, static)

    // todo:
//    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
//        value!!.print(instance.print(builder.indent(indent).append('[').append(token.text).append(if (static) " (static)" else " (nostatic)").append('\n').indent(indent + 1).append("name = ").append(name).append('\n'), indent + 1).append('\n'), indent + 1).append('\n').indent(indent).append(']')
}