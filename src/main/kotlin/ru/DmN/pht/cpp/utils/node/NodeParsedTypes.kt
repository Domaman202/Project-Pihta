package ru.DmN.pht.cpp.utils.node

import ru.DmN.pht.cpp.utils.node.NodeTypes.ANN_NATIVE_
import ru.DmN.pht.utils.node.IParsedNodeType
import ru.DmN.siberia.utils.node.INodeType

enum class NodeParsedTypes(override val operation: String, override val processed: INodeType) : IParsedNodeType {
    // @
    ANN_NATIVE("@native", ANN_NATIVE_);

    override val processable: Boolean
        get() = true
    override val compilable: Boolean
        get() = false
}