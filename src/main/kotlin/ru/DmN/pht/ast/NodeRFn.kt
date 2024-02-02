package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType
import ru.DmN.siberia.utils.indent

class NodeRFn(info: INodeInfo, var type: VirtualType?, var lambda: VirtualMethod?, val instance: Node, val name: String, var method: VirtualMethod?) : Node(info) {
    override fun copy(): NodeRFn =
        NodeRFn(info, type, lambda, instance, name, method)

    override fun print(builder: StringBuilder, indent: Int, short: Boolean): StringBuilder = builder.apply {
        indent(indent).append('(').append(info.type).append('\n')
        type?.let { indent(indent + 1).append("(type = ").append(it.name).append(")\n") }
        lambda?.let { indent(indent + 1).append("(lambda = ").append(it.name).append(it.desc).append(")\n") }
        method?.let { indent(indent + 1).append("(method = ").append(it.name).append(it.desc).append(")\n") }
        indent(indent + 1).append("(name = ").append(name).append(")\n")
            .indent(indent + 1).append("(instance:\n")
        instance.print(builder, indent + 2, short).append('\n').indent(indent + 1).append(")\n")
            .indent(indent).append(']')
    }
}