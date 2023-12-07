package ru.DmN.phtx.pls

import ru.DmN.phtx.pls.ups.NUPIncludeLzr
import ru.DmN.phtx.pls.ups.NUPLzr
import ru.DmN.siberia.utils.Module
import ru.DmN.siberia.utils.adda

// Pihta Lazurite Sublanguage
object PLS : Module("phtx/pls") {
    init {
        adda("inc-lzr", NUPIncludeLzr)
        adda("lzr",     NUPLzr)
    }
}