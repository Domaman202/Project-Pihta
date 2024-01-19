package ru.DmN.phtx.ppl.node

import ru.DmN.siberia.node.INodeType
import ru.DmN.siberia.node.NodeTypes.Type
import ru.DmN.siberia.node.NodeTypes.Type.PARSED
import ru.DmN.siberia.node.NodeTypes.Type.PROCESSED

enum class NodeTypes : INodeType {
    // l
    LIST("list", PARSED),
    // p
    PRESENTATION("presentation", PARSED),
    // t
    TITLE("title", PARSED);

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