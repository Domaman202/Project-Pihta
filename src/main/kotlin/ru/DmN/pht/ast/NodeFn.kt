package ru.DmN.pht.ast

import ru.DmN.pht.utils.NVC
import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.ast.INodesList
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType

class NodeFn(
    info: INodeInfo,
    val source: MutableList<Node>,
    var type: VirtualType?,
    val args: List<String>,
    val name: String,
    val refs: List<NVC>,
    var processed: MutableList<Node>? = null
) : BaseNode(info), INodesList {
    override val nodes: MutableList<Node>
        get() = processed ?: source

    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('[').append(info.type).append('\n')
            .indent(indent + 1).append("(source:")
        if (source.isNotEmpty()) {
            builder.append('\n')
            source.forEach { it.print(builder, indent + 2, short).append('\n') }
            builder.indent(indent + 1)
        }
        append(")\n").indent(indent + 1).append("(processed:")
        processed?.let { it ->
            builder.append('\n')
            it.forEach { it.print(builder, indent + 2, short).append('\n') }
            builder.indent(indent + 1)
        }
        append(")\n").indent(indent).append(']')
    }
}