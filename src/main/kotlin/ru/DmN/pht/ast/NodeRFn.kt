package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType

class NodeRFn(
    info: INodeInfo,
    var type: VirtualType?,
    var lambda: VirtualMethod?,
    val instance: Node,
    val name: String,
    var method: VirtualMethod?
) : Node(info) {
    override fun copy(): NodeRFn =
        NodeRFn(info, type, lambda, instance, name, method)
}