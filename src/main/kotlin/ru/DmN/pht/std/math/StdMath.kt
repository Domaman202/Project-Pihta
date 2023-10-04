package ru.DmN.pht.std.math

import ru.DmN.pht.std.base.ups.NUPDefault
import ru.DmN.pht.std.base.utils.StdModule
import ru.DmN.pht.std.math.ups.NUPEqual
import ru.DmN.pht.std.math.ups.NUPMath

object StdMath : StdModule("std/math") {
    init {
        add("<=>",NUPDefault)
        for (name in listOf("=", "!=", "<", "<=", ">=", ">"))
            add(name,   NUPEqual)
        for (name in listOf("+", "-", "*", "/"))
            add(name,   NUPMath)
        add("!",  NUPDefault)
    }
}