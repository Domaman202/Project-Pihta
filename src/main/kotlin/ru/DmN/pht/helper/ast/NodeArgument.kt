package ru.DmN.pht.helper.ast

import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.utils.node.INodeInfo

class NodeArgument(info: INodeInfo, override val value: Any?) : BaseNode(info), IValueNode