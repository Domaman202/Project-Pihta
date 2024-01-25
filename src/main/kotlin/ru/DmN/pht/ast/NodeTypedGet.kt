package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualType

class NodeTypedGet(info: INodeInfo, val name: String, val type: VirtualType) : Node(info)