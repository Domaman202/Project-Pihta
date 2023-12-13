package ru.DmN.phtx.rxx

import ru.DmN.phtx.rxx.ups.NUPIncRxx
import ru.DmN.siberia.utils.Module
import ru.DmN.siberia.utils.adda

object Rxx : Module("phtx/rxx") {
    init {
        adda("inc-rxx", NUPIncRxx)
    }
}