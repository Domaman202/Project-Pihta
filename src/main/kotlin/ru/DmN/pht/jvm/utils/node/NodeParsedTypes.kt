package ru.DmN.pht.jvm.utils.node

import ru.DmN.pht.jvm.utils.node.NodeTypes.*
import ru.DmN.pht.utils.node.IParsedNodeType
import ru.DmN.siberia.utils.node.INodeType

enum class NodeParsedTypes(override val operation: String, override val processed: INodeType) : IParsedNodeType {
    // @
    ANN_ANN("@annotation", ANN_ANN_), // @annotation
    ANN_LIST("@list", ANN_LIST_),     // @list
    ANN_SYNC("@sync", ANN_SYNC_);     // @synchronized

    override val processable: Boolean
        get() = true
    override val compilable: Boolean
        get() = false
}