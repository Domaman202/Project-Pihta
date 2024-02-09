package ru.DmN.phtx.ppl.node

import ru.DmN.siberia.node.INodeType
import ru.DmN.siberia.node.NodeTypes.Type
import ru.DmN.siberia.node.NodeTypes.Type.PARSED

enum class NodeTypes : INodeType {
    // a
    A_SIZED("a-sized", PARSED),
    A_OFFSET("a-offset", PARSED),
    // c
    C_PAIR("c-pair", PARSED),
    C_TRIPLE("c-triple", PARSED),
    C_FOURFOLD("c-fourfold", PARSED),
    // e
    E_IMAGE("e-image", PARSED),
    E_TEXT("e-text", PARSED),
    E_TITLE("e-title", PARSED),
    // i
    INC_IMG("inc-img", PARSED),
    INC_TXT("inc-txt", PARSED),
    // p
    PAGE_FRACTAL("page-fractal", PARSED),
    PAGE_LIST("page-list", PARSED),
    PRESENTATION("presentation", PARSED);

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