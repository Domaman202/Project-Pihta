package ru.DmN.pht.std.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.node.INodeInfo

class NodeGetA(info: INodeInfo, val name: String, val type: Type) : Node(info) {
    enum class Type {
        UNKNOWN,
        VARIABLE,
        THIS_FIELD,
        THIS_STATIC_FIELD
    }
}