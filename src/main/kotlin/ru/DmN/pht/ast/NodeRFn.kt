package ru.DmN.pht.ast

import ru.DmN.pht.jvm.utils.vtype.desc
import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.indent
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualMethod
import ru.DmN.siberia.utils.vtype.VirtualType

class NodeRFn(
    info: INodeInfo,
    var type: VirtualType?,
    var lambda: VirtualMethod?,
    var instance: Node?,
    val name: String,
    var method: VirtualMethod?
) : BaseNode(info) {
//    override fun copy(): NodeRFn =
//        NodeRFn(info, type, lambda, instance, name, method) // todo: А зачем?

    override fun print(builder: StringBuilder, indent: Int): StringBuilder = builder.apply {
        indent(indent).append('(').append(info.type).append('\n')
        type?.let { indent(indent + 1).append("(type = ").append(it.name).append(")\n") }
        lambda?.let { indent(indent + 1).append("(lambda = ").append(it.name).append(it.desc).append(")\n") }
        method?.let { indent(indent + 1).append("(method = ").append(it.name).append(it.desc).append(")\n") }
        indent(indent + 1).append("(name = ").append(name).append(")\n")
        instance?.let {
            indent(indent + 1).append("(instance:\n")
            it.print(builder, indent + 2).append('\n').indent(indent + 1).append(")\n")
        }
        indent(indent).append(']')
    }
}