package ru.DmN.pht.std.ast

import ru.DmN.pht.std.utils.text
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.indent

class NodeEField(info: INodeInfo, val fields: List<Pair<String, List<Node>>>) : Node(info) {
//    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
//        indent(indent).append('[').append(text)
//        if (fields.isNotEmpty()) {
//            append('\n')
//            fields.forEach { it ->
//                indent(indent + 1).append('(').append(it.first).append(')').append('\n')
//                it.second.forEach { it.print(builder, indent + 2).append('\n') }
//            }
//            indent(indent)
//        }
//        append(']')
//    }
}