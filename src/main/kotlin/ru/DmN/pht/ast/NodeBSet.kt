package ru.DmN.pht.ast

import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo

class NodeBSet(
    info: INodeInfo,
    val block: String,
    val variable: Variable,
    val value: Node
) : BaseNode(info) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
        indent(indent + 1).append("(block = ").append(block).append(")\n")
        indent(indent + 1).append("(name = ").append(variable.name).append(")\n")
        value.print(this, indent + 1).append('\n')
        indent(indent).append(']')
    }
}