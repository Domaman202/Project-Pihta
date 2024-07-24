package ru.DmN.pht.processor.utils

import ru.DmN.siberia.utils.vtype.VirtualType

object CastableAutoImpl : ICastable {
    override fun castableTo(to: VirtualType): Int = 1
}