package ru.DmN.pht.std.processor.utils

import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.std.utils.lenArgs
import ru.DmN.siberia.utils.VTDynamic

data class CastableImpl(val type: VirtualType) : ICastable {
    override fun castableTo(to: VirtualType): Int =
        if (to == VTDynamic)
            CastableDynamicImpl.castableTo(type)
        else lenArgs(type, to)
}
