package ru.DmN.pht.std.value

import ru.DmN.pht.base.processor.processors.NRDefault
import ru.DmN.pht.base.unparser.unparsers.NUNodesList
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.base.compiler.java.compilers.NCGetOrName
import ru.DmN.pht.std.base.compiler.java.compilers.NCValn
import ru.DmN.pht.std.base.compiler.java.compilers.NCValue
import ru.DmN.pht.std.base.parsers.*
import ru.DmN.pht.std.base.unparsers.NUGetOrName
import ru.DmN.pht.std.base.unparsers.NUValue

object StdValue : Module("std/value") {
    init {
        add("get-or-name!", NPGetOrName,NUGetOrName,    NRDefault,  NCGetOrName)
        add("valn!",        NPValnB)
        add("valn",         NPValnA,    NUNodesList,    NRDefault,  NCValn)
        add("value!",       NPValueB)
        add("value",        NPValueA,   NUValue,        NRDefault,  NCValue)
    }
}