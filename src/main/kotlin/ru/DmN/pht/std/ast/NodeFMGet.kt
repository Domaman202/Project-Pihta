package ru.DmN.pht.std.ast

import ru.DmN.pht.std.utils.text
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.indent

class NodeFMGet(info: INodeInfo, val instance: Node, name: String, static: Boolean, val native: Boolean = false) : NodeGetOrName(info, name, static), INodesList {
    override val nodes: MutableList<Node> = mutableListOf()

    override fun isLiteral(): Boolean =
        false

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        instance.print(builder.indent(indent).append('[').append(text).append(if (static) " (static)" else " (nostatic)").append('\n').indent(indent + 1).append("name = ").append(name).append('\n'), indent + 1).append('\n').indent(indent).append(']')
}