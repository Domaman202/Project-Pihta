package ru.DmN.pht.ast

import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType

class NodeToEFn(
    info: INodeInfo,
    val type: VirtualType,
    val name: String,
    val args: List<VirtualType>
) : BaseNode(info) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
        indent(indent + 1).append("(type = ").append(type.name).append(")\n")
        indent(indent + 1).append("(name = ").append(name).append(")\n")
        indent(indent + 1).append("(args = [").append(args.joinToString(", ") { it.name }).append("])\n")
        indent(indent).append(']')
    }
}