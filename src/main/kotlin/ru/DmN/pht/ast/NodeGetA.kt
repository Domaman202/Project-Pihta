package ru.DmN.pht.std.ast

import ru.DmN.pht.ast.IGetNode
import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.ast.NodeNodesList
import ru.DmN.siberia.node.INodeInfo

class NodeGetA(info: INodeInfo, nodes: MutableList<Node>, override val name: String, override val type: Type) : NodeNodesList(info, nodes), IGetNode {
    enum class Type {
        VARIABLE,
        THIS_FIELD,
        THIS_STATIC_FIELD
    }
}