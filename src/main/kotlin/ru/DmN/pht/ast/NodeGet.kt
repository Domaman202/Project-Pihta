package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.indent

class NodeGet(info: INodeInfo, val name: String, val type: Type) : Node(info) {
    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type)
        if (short)
            append(
                when (type) {
                    Type.VARIABLE -> " var "
                    Type.THIS_FIELD -> " this field "
                    Type.THIS_STATIC_FIELD -> " this static field "
                }
            ).append(name).append(']')
        else append('\n').indent(indent + 1).append("(name = ").append(name).append(")\n")
            .indent(indent + 1).append("(type = ")
            .append(
                when (type) {
                    Type.VARIABLE -> "var)\n"
                    Type.THIS_FIELD -> "this field)\n"
                    Type.THIS_STATIC_FIELD -> "this static field)\n"
                }
            )
            .indent(indent).append(']')
    }

    enum class Type {
        VARIABLE,
        THIS_FIELD,
        THIS_STATIC_FIELD
    }
}