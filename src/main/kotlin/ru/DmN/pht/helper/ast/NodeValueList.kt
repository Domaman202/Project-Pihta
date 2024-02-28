package ru.DmN.pht.helper.ast

import ru.DmN.siberia.ast.BaseNode
import ru.DmN.siberia.utils.node.INodeInfo

class NodeValueList(info: INodeInfo, override val value: List<Any?>) : BaseNode(info), IValueNode