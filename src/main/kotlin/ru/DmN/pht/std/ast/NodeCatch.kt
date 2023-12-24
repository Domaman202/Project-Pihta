package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualType

class NodeCatch(info: INodeInfo, nodes: MutableList<Node>, val catchers: List<Triple<String, VirtualType, Node?>>) : NodeNodesList(info, nodes)