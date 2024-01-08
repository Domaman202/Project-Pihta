package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo

class NodeInner(info: INodeInfo, val field: String, val type: String) : Node(info)