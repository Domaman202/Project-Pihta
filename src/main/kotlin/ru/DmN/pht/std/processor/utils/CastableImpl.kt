package ru.DmN.pht.std.processor.utils

import ru.DmN.siberia.utils.VirtualType
import ru.DmN.pht.std.utils.lenArgs
import ru.DmN.siberia.utils.VTDynamic

data class CastableImpl(val type: VirtualType) : ICastable {
    override fun castableTo(to: VirtualType): Int =
        if (to == VTDynamic)
            if (to.isArray)
                2
            else if (to.isPrimitive)
                1
            else 0
        else lenArgs(type, to)
}
