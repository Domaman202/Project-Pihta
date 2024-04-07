package ru.DmN.pht.cpp.utils.node

import ru.DmN.siberia.utils.node.INodeType
import ru.DmN.siberia.utils.node.NodeTypes.Type
import ru.DmN.siberia.utils.node.NodeTypes.Type.PARSED
import ru.DmN.siberia.utils.node.NodeTypes.Type.PROCESSED

enum class NodeTypes : INodeType {
    // @
    ANN_NATIVE_("@native", PROCESSED);

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