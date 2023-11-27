package ru.DmN.pht.std.processor.utils

import ru.DmN.siberia.utils.VirtualType

class CastableDynamicImpl : ICastable {
    override fun castableTo(to: VirtualType): Int =
        if (to.isArray)
            2
        else if (to.isPrimitive)
            1
        else 0
}