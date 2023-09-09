package ru.DmN.pht.std.module

import ru.DmN.pht.std.base.ups.UPDefault
import ru.DmN.pht.std.base.utils.StdModule
import ru.DmN.pht.std.module.compiler.java.compilers.NCModule
import ru.DmN.pht.std.module.parsers.NPModule

object StdModule : StdModule("std/module", true) {
    init {
        add("module", NPModule, UPDefault,  UPDefault,  NCModule)
    }
}