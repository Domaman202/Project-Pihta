package ru.DmN.pht.helper.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo

class NodeArgument(info: INodeInfo, override val value: Any?) : Node(info), IValueNode