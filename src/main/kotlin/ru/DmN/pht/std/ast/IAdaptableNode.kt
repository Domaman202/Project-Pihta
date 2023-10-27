package ru.DmN.pht.std.ast

import ru.DmN.pht.base.utils.VirtualType

interface IAdaptableNode {
    fun isAdaptableTo(type: VirtualType): Boolean

    fun adaptTo(type: VirtualType)
}