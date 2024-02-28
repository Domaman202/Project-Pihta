package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType

class NodeType(info: INodeInfo, nodes: MutableList<Node>, val type: VirtualType.VirtualTypeImpl) : NodeNodesList(info, nodes), IAbstractlyNode, IOpenlyNode {
    override var abstract: Boolean
        get() = type.isAbstract
        set(value) { type.isAbstract = value }
    override var open: Boolean
        get() = !type.isFinal
        set(value) { type.isFinal = !value }

    override fun copy(): NodeType =
        NodeType(info, copyNodes(), type)

    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
            .indent(indent + 1).append("(type = ").append(
                if (type.isInterface)
                    "interface"
                else if (type.isAbstract)
                    "abstract class"
                else if (type.isFinal)
                    "final class"
                else "open class"
            ).append(")\n")
            .indent(indent + 1).append("(name = ").append(type.name).append(')')
        if (!short && type.parents.isNotEmpty()) {
            append('\n').indent(indent + 1).append("(parents = [")
            type.parents.forEachIndexed { i, it ->
                append(it.name)
                if (i + 1 < type.parents.size) {
                    append(' ')
                }
            }
            append("])")
        }
        printNodes(this, indent, short).append(']')
    }
}