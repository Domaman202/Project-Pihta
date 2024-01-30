package ru.DmN.pht.ast

import ru.DmN.pht.node.NodeTypes
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.indent

open class NodeGetOrName(info: INodeInfo, val name: String, val static: Boolean) : Node(info), IValueNode { // todo: А зачем тут static?
    override fun isLiteral(): Boolean =
        true

    override fun getValueAsString(): String =
        name

    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
            .indent(indent + 1).append("(type = ").append(if (static) "STATIC" else "NO-STATIC").append(")\n")
            .indent(indent + 1).append("(text = '").append(name).append("')\n")
            .indent(indent).append(']')
    }

    init {
        if (info.type == NodeTypes.GET_OR_NAME && (name == "this") && Thread.currentThread().stackTrace.find { it.className.contains("parsers") } == null) {
            RuntimeException("DUMP").printStackTrace()
        }
    }
}