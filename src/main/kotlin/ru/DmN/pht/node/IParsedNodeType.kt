package ru.DmN.pht.node

import ru.DmN.siberia.node.INodeType

interface IParsedNodeType : INodeType {
    val processed: INodeType
}