package ru.DmN.pht.ast

import ru.DmN.pht.utils.text
import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo

class NodeEField(info: INodeInfo, val fields: List<Pair<String, List<Node>>>) : BaseNode(info) {
    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(text)
        if (fields.isNotEmpty()) {
            append('\n')
            fields.forEach { it ->
                indent(indent + 1).append("[\n")
                    .indent(indent + 2).append("(name = ").append(it.first).append(')').append('\n')
                    .indent(indent + 2).append("(arguments: ").append('\n')
                it.second.forEach { it.print(builder, indent + 3, short).append('\n') }
                indent(indent + 2).append(")\n")
                    .indent(indent + 1).append("]\n")
            }
            indent(indent)
        }
        append(']')
    }
}