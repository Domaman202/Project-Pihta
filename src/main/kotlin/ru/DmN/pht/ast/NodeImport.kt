package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.indent

class NodeImport(info: INodeInfo, val module: String, val data: Map<String, List<Any?>>) : Node(info) {
    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
            .indent(indent + 1).append("(module = ").append(module).append(')')
        if (data.isNotEmpty()) {
            data.entries.forEach {
                append('\n').indent(indent + 1).append('(').append(it.key).append(" = [")
                it.value.forEachIndexed { i, v ->
                    append(v)
                    if (i + 1 < it.value.size) {
                        append(' ')
                    }
                }
                append("])")
            }
            append('\n')
        }
        indent(indent).append(']')
    }
}