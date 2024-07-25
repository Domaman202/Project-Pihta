package ru.DmN.pht.ast

import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.utils.node.INodeInfo
import ru.DmN.siberia.utils.vtype.VirtualType

class NodeToEFn(
    info: INodeInfo,
    val type: VirtualType,
    val name: String,
    val args: List<VirtualType>
) : BaseNode(info)