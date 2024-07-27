package ru.DmN.pht.imports.ast

import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo

class NodeAliasType(
    info: INodeInfo,
    val imports: List<Pair<String, String>>
) : BaseNode(info) {
    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type)
        if (imports.isNotEmpty()) {
            imports.forEach { (name, alias) ->
                append('\n').indent(indent + 1).append("[\n")
                indent(indent + 2).append("(name = ").append(name).append(")\n")
                indent(indent + 2).append("(type = ").append(alias).append(")\n")
                indent(indent + 1).append(']')
            }
            append('\n')
        }
        indent(indent).append(']')
    }
}