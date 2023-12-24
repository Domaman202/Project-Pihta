package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo

class NodeNamedList(info: INodeInfo, nodes: MutableList<Node>, val name: String) : NodeNodesList(info, nodes)