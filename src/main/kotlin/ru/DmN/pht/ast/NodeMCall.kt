package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.indent

class NodeMCall(info: INodeInfo, nodes: MutableList<Node>, val generic: VirtualType?, val instance: Node, val method: VirtualMethod, val type: Type) : NodeNodesList(info, nodes) {
    override fun copy(): NodeMCall =
        NodeMCall(info, copyNodes(), generic, instance, method, type)

    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
            .indent(indent + 1).append("(method = ").append(method.name).append(method.desc).append(")\n")
            .indent(indent + 1).append("(instance:\n")
        instance.print(builder, indent + 2, short).append('\n').indent(indent + 1).append(')')
        if (!short) {
            append('\n').indent(indent + 1).append("(generic = ").append(generic).append(")\n")
                .indent(indent + 1).append("(type = ").append(type).append(')')
            if (nodes.isEmpty())
                append('\n').indent(indent)
        }
        printNodes(builder, indent, short).append(']')
    }

    enum class Type {
        UNKNOWN,
        EXTEND,
        STATIC,
        VIRTUAL,
        SUPER,
        DYNAMIC
    }
}