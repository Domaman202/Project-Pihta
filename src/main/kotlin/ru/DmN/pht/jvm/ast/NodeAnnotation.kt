package ru.DmN.pht.jvm.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo
import ru.DmN.siberia.utils.VirtualType

class NodeAnnotation(info: INodeInfo, val type: VirtualType, val parameters: Map<String, Node>) : Node(info)