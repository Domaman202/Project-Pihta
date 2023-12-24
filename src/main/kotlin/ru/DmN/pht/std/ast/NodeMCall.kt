package ru.DmN.pht.std.ast

import ru.DmN.pht.std.utils.text
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.indent

class NodeMCall(info: INodeInfo, nodes: MutableList<Node>, val generic: VirtualType?, val instance: Node, val method: VirtualMethod, val type: Type) : NodeNodesList(info, nodes) {
    override fun copy(): NodeMCall =
        NodeMCall(info, copyNodes(), generic, instance, method, type)

    override fun print(builder: StringBuilder, indent: Int): StringBuilder {
        builder.indent(indent).append('[').append(text).append(" (").append(type).append(") ").append(method.name).append(method.desc).append('\n')
        instance.print(builder, indent + 1)
        if (nodes.isEmpty())
            builder.append('\n').indent(indent)
        return printNodes(builder, indent).append(']')
    }

    enum class Type {
        // for compiler
        UNKNOWN,
        EXTEND,
        STATIC,
        VIRTUAL,
        SUPER,
        DYNAMIC
    }
}