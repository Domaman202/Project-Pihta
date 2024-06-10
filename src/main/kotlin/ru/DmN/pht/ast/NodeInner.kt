package ru.DmN.pht.ast

import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.utils.node.INodeInfo

class NodeInner(
    info: INodeInfo,
    val field: String,
    val type: String
) : BaseNode(info)