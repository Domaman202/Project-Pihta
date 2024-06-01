package ru.DmN.pht.ast

import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.node.INodeInfo

class NodeBSet(info: INodeInfo, val block: String, val variable: Variable, val value: Node) : BaseNode(info)