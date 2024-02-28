package ru.DmN.pht.jvm.node

import ru.DmN.pht.jvm.node.NodeTypes.*
import ru.DmN.pht.utils.node.IParsedNodeType
import ru.DmN.siberia.utils.node.INodeType

enum class NodeParsedTypes(override val operation: String, override val processed: INodeType) : IParsedNodeType {
    // @
    ANN_ANN("@annotation", ANN_ANN_),
    ANN_LIST("@list", ANN_LIST_),
    ANN_SYNC("@synchronized", ANN_SYNC_);

    override val processable: Boolean
        get() = true
    override val compilable: Boolean
        get() = false
}