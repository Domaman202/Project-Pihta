package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualMethod
import ru.DmN.siberia.utils.VirtualType

class NodeRFn(
    info: INodeInfo,
    val type: VirtualType,
    val lambda: VirtualMethod,
    val instance: Node,
    val method: VirtualMethod
) : Node(info)