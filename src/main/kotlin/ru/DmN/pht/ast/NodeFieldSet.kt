package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo

class NodeFieldSet(
    info: INodeInfo,
    nodes: MutableList<Node>,
    val instance: Node,
    val name: String,
    val static: Boolean,
    val native: Boolean = false
) : NodeNodesList(info, nodes) {
    override fun copy(): NodeFieldSet =
        NodeFieldSet(info, copyNodes(), instance, name, static, native)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
        if (static || native) {
            indent(indent + 1).append('(')
            append(if (static && native) "static native" else if (static) "static" else "native")
            append(")\n")
        }
        indent(indent + 1).append("(name = ").append(name).append(")\n")
        indent(indent + 1).append("(instance:\n")
        instance.print(builder, indent + 2)
        append('\n').indent(indent + 1).append(")\n")
        nodes.forEach { it.print(builder, indent + 1).append('\n') }
        indent(indent)
        append(']')
    }
}