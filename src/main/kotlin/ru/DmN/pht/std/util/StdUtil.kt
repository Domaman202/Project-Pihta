package ru.DmN.pht.std.util

import ru.DmN.pht.std.base.processor.utils.clazz
import ru.DmN.pht.std.base.processor.utils.global
import ru.DmN.pht.std.base.processor.utils.method
import ru.DmN.pht.std.base.ups.NUPDefault
import ru.DmN.pht.std.base.utils.StdModule
import ru.DmN.pht.std.util.processors.NRCTSC
import ru.DmN.pht.std.util.processors.NRRandSymbol
import ru.DmN.pht.std.util.processors.NRSymbol
import ru.DmN.pht.std.util.processors.NRValnRepeat
import ru.DmN.pht.std.util.ups.NUPLazySymbol

object StdUtil : StdModule("std/util") {
    init {
        add("valn-repeat",  NUPDefault, NRValnRepeat)
        // Символы
        add("rand-symbol",  NUPDefault, NRRandSymbol)
        add("lazy-symbol",  NUPLazySymbol)
        add("symbol",       NUPDefault, NRSymbol)
        // Compile-Type Константы
        add("*type-name*",  NUPDefault, NRCTSC { _, ctx -> ctx.clazz.name })
        add("*fn-name*",    NUPDefault, NRCTSC { _, ctx -> ctx.method.name })
        add("*ns-name*",    NUPDefault, NRCTSC { _, ctx -> ctx.global.namespace })
    }
}