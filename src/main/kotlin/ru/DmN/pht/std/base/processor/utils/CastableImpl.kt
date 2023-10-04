package ru.DmN.pht.std.base.processor.utils

import ru.DmN.pht.base.utils.VirtualType
import ru.DmN.pht.std.base.utils.lenArgs

data class CastableImpl(val type: VirtualType) : ICastable {
    override fun castableTo(to: VirtualType): Int =
        lenArgs(type, to)
}
