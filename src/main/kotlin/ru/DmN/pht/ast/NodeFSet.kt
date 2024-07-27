package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualField

class NodeFSet(
    info: INodeInfo,
    nodes: MutableList<Node>,
    val field: VirtualField
) : NodeNodesList(info, nodes) {
    override fun copy(): NodeFSet =
        NodeFSet(info, copyNodes(), field)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
        field.modifiers.run {
            if (isStatic || isFinal) {
                indent(indent + 1).append('(')
                append(if (isStatic && isFinal) "static final" else if (isStatic) "static" else "final")
                append(")\n")
            }
        }
        indent(indent + 1).append("(name = ").append(field.name).append(")\n")
        indent(indent + 1).append("(type = ").append(field.type).append(")\n")
        nodes.forEach { it.print(builder, indent + 1).append('\n') }
        indent(indent)
        append(']')
    }
}