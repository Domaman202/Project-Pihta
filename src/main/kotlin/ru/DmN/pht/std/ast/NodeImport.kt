package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo

class NodeImport(info: INodeInfo, val module: String, val data: Map<String, List<Any?>>) : Node(info)