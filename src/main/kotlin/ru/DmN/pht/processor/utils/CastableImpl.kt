package ru.DmN.pht.processor.utils

import ru.DmN.pht.utils.lenArgs
import ru.DmN.pht.utils.vtype.VTAuto
import ru.DmN.pht.utils.vtype.VTDynamic
import ru.DmN.siberia.utils.vtype.VirtualType

data class CastableImpl(val type: VirtualType) : ICastable {
    override fun castableTo(to: VirtualType): Int =
        when (to) {
            VTAuto -> CastableAutoImpl.castableTo(type)
            VTDynamic -> CastableDynamicImpl.castableTo(type)
            else -> lenArgs(type, to)
        }
}
