package ru.DmN.pht.std.module

import ru.DmN.pht.std.ups.NUPDefault
import ru.DmN.pht.std.utils.StdModule
import ru.DmN.pht.std.module.parsers.NPModule

object StdModule : StdModule("pht/module", true) {
    init {
        add("module", NUPDefault, NPModule)
    }
}