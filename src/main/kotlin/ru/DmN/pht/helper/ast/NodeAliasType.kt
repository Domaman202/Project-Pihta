package ru.DmN.pht.helper.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo

class NodeAliasType(info: INodeInfo, val imports: List<Pair<String, String>>) : Node(info)