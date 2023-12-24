package ru.DmN.pht.std.ast

import ru.DmN.pht.std.utils.text
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.indent

open class NodeGetOrName(info: INodeInfo, val name: String, val static: Boolean) : Node(info), IValueNode {
    override fun isLiteral(): Boolean =
        true

    override fun getValueAsString(): String =
        name

    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
        builder.indent(indent).append('[').append(text).append(']').append(if (static) " (static) " else " (nostatic) ").append(name)
}