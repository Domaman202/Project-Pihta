package ru.DmN.pht.helper.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo

class NodeValueList(info: INodeInfo, override val value: List<Any?>) : Node(info), IValueNode