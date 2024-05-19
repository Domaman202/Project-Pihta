package ru.DmN.pht.jvm.utils.node

import ru.DmN.siberia.utils.node.INodeType
import ru.DmN.siberia.utils.node.NodeTypes.Type
import ru.DmN.siberia.utils.node.NodeTypes.Type.PARSED
import ru.DmN.siberia.utils.node.NodeTypes.Type.PROCESSED

enum class NodeTypes : INodeType {
    // c
    CLS_OF("cls-of", PARSED),           // class-of
    CLS_OF_("cls-of", PROCESSED),       // class-of
    // s
    SBLOCK("sblock", PARSED),           // sync-block
    SBLOCK_("sblock", PROCESSED),       // sync-block
    SYMBOL_CLS("symbol-cls", PARSED),   // symbol-cls

    // @
    ANN_ANN_("@ann", PROCESSED),        // @annotation
    ANN_LIST_("@list", PROCESSED),      // @list
    ANN_SYNC_("@sync", PROCESSED);      // @sync

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