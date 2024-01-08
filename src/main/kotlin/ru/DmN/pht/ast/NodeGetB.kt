package ru.DmN.pht.std.ast

import ru.DmN.pht.ast.IGetNode
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo

class NodeGetB(info: INodeInfo, override val name: String, override val type: NodeGetA.Type) : Node(info), IGetNode