package ru.DmN.pht.std.fp

import ru.DmN.pht.std.base.parsers.NPGetB
import ru.DmN.pht.std.base.parsers.NPSetA
import ru.DmN.pht.std.fp.ups.NUPDef
import ru.DmN.pht.std.base.ups.NUPDefault
import ru.DmN.pht.std.fp.ups.NUPGetA
import ru.DmN.pht.std.fp.ups.NUPSetB
import ru.DmN.pht.std.base.utils.StdModule
import ru.DmN.pht.std.fp.compiler.java.StdFPJava
import ru.DmN.pht.std.fp.processor.processors.*
import ru.DmN.pht.std.fp.ups.NUPDefn

object StdFP : StdModule("std/fp") {
    init {
        // Функция
        add("defn", NUPDefn)
        // Тело
        add("body",     NUPDefault, NRBody)
        // Сеттеры
        add("set",      NPSetA, null, null)
        add("set!",     NUPSetB)
        // Геттеры
        add("get",      NUPGetA)
        add("get!",     NPGetB, null, null)
        // Переменные
        add("def",      NUPDef)
        // Работа с типами
        add("as",       NUPDefault, NRAs)
        add("is",       NUPDefault, NRIs)
        add("typeof",   NUPDefault, NRTypeof)
        // Возвраты
        add("ret",      NUPDefault, NRRet)
        add("yield",    NUPDefault, NRYield)

        StdFPJava.init()
    }
}