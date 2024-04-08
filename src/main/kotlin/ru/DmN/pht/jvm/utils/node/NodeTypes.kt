package ru.DmN.pht.jvm.utils.node

import ru.DmN.siberia.utils.node.INodeType
import ru.DmN.siberia.utils.node.NodeTypes.Type
import ru.DmN.siberia.utils.node.NodeTypes.Type.PARSED
import ru.DmN.siberia.utils.node.NodeTypes.Type.PROCESSED

enum class NodeTypes : INodeType {
    // c
    CLASS_OF("class-of", PARSED),
    CLASS_OF_("class-of", PROCESSED),
    // s
    SYNC("sync", PARSED),
    SYNC_("sync", PROCESSED),

    // @
    ANN_ANN_("@annotation", PROCESSED),
    ANN_LIST_("@list", PROCESSED),
    ANN_SYNC_("@synchronized", PROCESSED);

    override val operation: String
    override val processable: Boolean
    override val compilable: Boolean

    constructor(operation: String, processable: Boolean, compilable: Boolean) {
        this.operation = operation
        this.processable = processable
        this.compilable = compilable
    }

    constructor(operation: String, type: Type) {
        this.operation = operation
        if (type == PARSED) {
            this.processable = true
            this.compilable = false
        } else {
            this.processable = false
            this.compilable = true
        }
    }
}