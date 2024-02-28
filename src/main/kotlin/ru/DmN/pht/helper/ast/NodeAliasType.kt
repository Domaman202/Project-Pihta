package ru.DmN.pht.helper.ast

import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.utils.node.INodeInfo

class NodeAliasType(info: INodeInfo, val imports: List<Pair<String, String>>) : BaseNode(info)