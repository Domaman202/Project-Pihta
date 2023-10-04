package ru.DmN.pht.std.value

import ru.DmN.pht.std.base.ups.NUPDefault
import ru.DmN.pht.std.base.utils.StdModule
import ru.DmN.pht.std.value.compiler.java.StdValueJava
import ru.DmN.pht.std.value.parsers.*
import ru.DmN.pht.std.value.ups.NUPGetOrName
import ru.DmN.pht.std.value.ups.NUPValue

object StdValue : StdModule("std/value") {
    init {
        // Имя или get
        add("get-or-name!", NUPGetOrName)
        // Список выражений
        add("valn",         NUPDefault,     NPValnA)
        add("valn!",        NPValnB, null, null)
        // Значение
        add("value",        NUPValue)
        add("value!",       NPValueB, null, null)

        StdValueJava.init()
    }
}