package ru.DmN.pht.ast

import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.utils.Variable
import ru.DmN.siberia.utils.node.INodeInfo

class NodeBGet(info: INodeInfo, val variable: Variable) : BaseNode(info)