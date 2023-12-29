package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualField.VirtualFieldImpl

class NodeFieldB(info: INodeInfo, val fields: List<VirtualFieldImpl>) : Node(info), IStaticallyNode, IFinallyNode {
    override var static: Boolean = false
        set(value) { field = value; fields.forEach { it.isStatic = value } }
    override var final: Boolean = false

//    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
//        indent(indent).append('[').append(text).append(' ')
//            .append(if (static) "(static)" else "(nostatic)").append(' ')
//            .append(if (final) "(final)" else "(nofinal)")
//        if (fields.isNotEmpty()) {
//            fields.forEach {
//                append('\n').indent(indent + 1).append("[\n")
//                    .indent(indent + 2).append("name = ").append(it.name)
//                    .append('\n').indent(indent + 2).append("type = ").append(it.type.name)
//                append('\n').indent(indent + 1).append(']')
//            }
//            append('\n').indent(indent)
//        }
//        append(']')
//    }
}