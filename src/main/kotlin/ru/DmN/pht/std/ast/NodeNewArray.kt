package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualType

class NodeNewArray(info: INodeInfo, val type: VirtualType, val size: Node) : Node(info) {
//    override fun print(builder: StringBuilder, indent: Int): StringBuilder =
//        size.print(
//            builder.indent(indent).append('[').append(text)
//                .append('\n').indent(indent + 1).append(type.name)
//                .append('\n'), indent + 1)
//            .append('\n').indent(indent).append(']')
}