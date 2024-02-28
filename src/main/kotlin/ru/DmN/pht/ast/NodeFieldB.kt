package ru.DmN.pht.ast

import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualField

class NodeFieldB(info: INodeInfo, val fields: List<VirtualField.VirtualFieldImpl>) : BaseNode(info), IStaticallyNode, IOpenlyNode, IFinallyNode {
    override var static: Boolean = false
        set(value) { field = value; fields.stream().map { it.modifiers }.forEach { it.isStatic = value } }
    override var final: Boolean = false
        set(value) { field = value; fields.stream().map { it.modifiers }.forEach { it.isFinal = value } }
    override var open: Boolean = false

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