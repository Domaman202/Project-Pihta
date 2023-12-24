package ru.DmN.pht.std.imports.node

import ru.DmN.siberia.node.INodeType

enum class NodeTypes(override val operation: String) : INodeType {
    TYPES("types"),
    EXTENSIONS("extensions"),
    MACROS("macros");

    override val processable: Boolean
        get() = false
    override val compilable: Boolean
        get() = false
}