package ru.DmN.pht.ast

import ru.DmN.pht.std.ast.NodeGetOrName
import ru.DmN.pht.std.node.NodeTypes
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualType

class NodeTypedGet(info: INodeInfo, name: String, val type: VirtualType) : NodeGetOrName(info, name, false) {
    companion object {
        fun of(node: NodeGetOrName, type: VirtualType) =
            NodeTypedGet(node.info.withType(NodeTypes.TYPED_GET), node.name, type)
    }
}