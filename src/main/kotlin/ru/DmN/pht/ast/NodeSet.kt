package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo

class NodeSet(info: INodeInfo, val name: String, val value: Node) : Node(info)