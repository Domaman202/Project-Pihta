package ru.DmN.pht.helper.node

import ru.DmN.siberia.utils.node.INodeType

enum class NodeTypes(override val operation: String) : INodeType {
    TYPES("types"),
    EXTENSIONS("extensions"),
    MACROS("macros"),
    METHODS("methods");

    override val processable: Boolean
        get() = false
    override val compilable: Boolean
        get() = false
}