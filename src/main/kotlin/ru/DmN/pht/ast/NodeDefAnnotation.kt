package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo

class NodeDefAnnotation(info: INodeInfo, val name: String, val data: Map<String, List<Any?>>) : Node(info)