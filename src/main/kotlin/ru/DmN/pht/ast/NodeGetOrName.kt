package ru.DmN.pht.ast

import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo

open class NodeGetOrName(
    info: INodeInfo,
    val name: String,
    val static: Boolean
) : BaseNode(info), IValueNode { // todo: А зачем тут static?
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
}