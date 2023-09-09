package ru.DmN.pht.std.value

import ru.DmN.pht.base.unparser.unparsers.NUNodesList
import ru.DmN.pht.std.base.compiler.java.compilers.NCGetOrName
import ru.DmN.pht.std.base.compiler.java.compilers.NCValn
import ru.DmN.pht.std.base.compiler.java.compilers.NCValue
import ru.DmN.pht.std.base.parsers.*
import ru.DmN.pht.std.base.unparsers.NUGetOrName
import ru.DmN.pht.std.base.unparsers.NUValue
import ru.DmN.pht.std.base.ups.UPDefault
import ru.DmN.pht.std.base.utils.StdModule

object StdValue : StdModule("std/value") {
    init {
        add("get-or-name!", NPGetOrName,NUGetOrName,    UPDefault,  NCGetOrName)
        add("valn!",        NPValnB)
        add("valn",         NPValnA,    NUNodesList,    UPDefault,  NCValn)
        add("value!",       NPValueB)
        add("value",        NPValueA,   NUValue,        UPDefault,  NCValue)
    }
}