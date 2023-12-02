package ru.DmN.pht.std.ast

import ru.DmN.siberia.utils.VirtualType

interface IGenericsNode<T : IGenericsNode<T>> {
    val generics: List<VirtualType>

    fun withGenerics(generics: List<VirtualType>): T
}