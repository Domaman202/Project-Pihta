package ru.DmN.pht.ast

import ru.DmN.pht.jvm.utils.vtype.desc
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

class NodeMCall(
    info: INodeInfo,
    nodes: MutableList<Node>,
    val generic: VirtualType?,
    val instance: Node,
    var method: VirtualMethod,
    val type: Type,
    /**
     * Inline | Generation
     */
    var special: Node? = null
) : NodeNodesList(info, nodes) {

    override fun copy(): NodeMCall =
        NodeMCall(info, copyNodes(), generic, instance, method, type, special)

    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
            .indent(indent + 1).append("(method = ").append(method.name).append(method.desc).append(")\n")
            .indent(indent + 1).append("(instance:\n")
        instance.print(builder, indent + 2, short).append('\n').indent(indent + 1).append(')').append('\n').indent(indent)
        if (!short) {
            indent(1).append("(generic = ").append(generic).append(")\n").indent(indent + 1).append("(type = ").append(type).append(')')
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