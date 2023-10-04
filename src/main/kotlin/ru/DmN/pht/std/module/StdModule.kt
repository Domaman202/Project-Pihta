package ru.DmN.pht.std.module

import ru.DmN.pht.std.base.ups.NUPDefault
import ru.DmN.pht.std.base.utils.StdModule
import ru.DmN.pht.std.module.parsers.NPModule

object StdModule : StdModule("std/module", true) {
    init {
        add("module", NUPDefault, NPModule)
    }
}