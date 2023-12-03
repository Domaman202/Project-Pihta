package ru.DmN.phtx.pls

import ru.DmN.phtx.pls.processors.NRIncludeLzr
import ru.DmN.phtx.pls.ups.NUPLzr
import ru.DmN.siberia.ups.NUPDefault
import ru.DmN.siberia.utils.Module
import ru.DmN.siberia.utils.adda

// Pihta Lazurite Sublanguage
object PLS : Module("phtx/pls") {
    init {
        adda("include-lzr", NUPDefault, NRIncludeLzr)
        adda("lzr",         NUPLzr)
    }
}