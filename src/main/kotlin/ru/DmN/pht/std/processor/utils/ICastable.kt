package ru.DmN.pht.std.processor.utils

import ru.DmN.pht.base.utils.VirtualType

interface ICastable {
    /**
     * -1 -> no castable
     * N -> cast cost (How far is the type from the desired type)
     */
    fun castableTo(to: VirtualType): Int

    companion object {
        fun of(type: VirtualType) =
            CastableImpl(type)
    }
}