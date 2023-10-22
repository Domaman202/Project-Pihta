package ru.DmN.pht.std.processor.utils

import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.utils.lenArgs

data class CastableImpl(val type: VirtualType) : ICastable {
    override fun castableTo(to: VirtualType): Int =
        lenArgs(type, to)
}
