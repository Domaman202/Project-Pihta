package ru.DmN.pht.std.node

import ru.DmN.siberia.node.INodeType

interface IParsedNodeType : INodeType {
    val processed: INodeType
}