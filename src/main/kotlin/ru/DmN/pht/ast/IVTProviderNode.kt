package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.vtype.VirtualType

interface IVTProviderNode<T : VirtualType> : Node {
    val type: T
}