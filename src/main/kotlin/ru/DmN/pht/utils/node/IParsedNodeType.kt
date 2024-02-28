package ru.DmN.pht.utils.node

import ru.DmN.siberia.utils.node.INodeType

interface IParsedNodeType : INodeType {
    val processed: INodeType
}