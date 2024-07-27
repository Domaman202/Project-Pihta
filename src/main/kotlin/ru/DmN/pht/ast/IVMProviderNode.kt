package ru.DmN.pht.ast

import ru.DmN.siberia.ast.Node
import ru.DmN.siberia.utils.vtype.VirtualMethod

interface IVMProviderNode<T : VirtualMethod> : Node {
    val method: T
}