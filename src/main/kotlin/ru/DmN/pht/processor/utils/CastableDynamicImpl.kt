package ru.DmN.pht.processor.utils

import ru.DmN.siberia.utils.vtype.VirtualType

object CastableDynamicImpl : ICastable {
    override fun castableTo(to: VirtualType): Int =
        if (to.isArray)
            castableTo(to.componentType!!) * 100
        else if (to.isPrimitive)
            10
        else 1
}