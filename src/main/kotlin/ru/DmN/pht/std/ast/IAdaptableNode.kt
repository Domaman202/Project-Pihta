package ru.DmN.pht.std.ast

import ru.DmN.siberia.utils.VirtualType

interface IAdaptableNode {
    fun isAdaptableTo(type: VirtualType): Boolean

    fun adaptTo(type: VirtualType)
}