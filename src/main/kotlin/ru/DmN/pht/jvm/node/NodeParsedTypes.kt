package ru.DmN.pht.jvm.node

import ru.DmN.pht.jvm.node.NodeTypes.ANN_SYNC_
import ru.DmN.pht.node.IParsedNodeType
import ru.DmN.siberia.node.INodeType

enum class NodeParsedTypes(override val operation: String, override val processed: INodeType) : IParsedNodeType {
    // @
    ANN_SYNC("@synchronized", ANN_SYNC_);

    override val processable: Boolean
        get() = true
    override val compilable: Boolean
        get() = false
}