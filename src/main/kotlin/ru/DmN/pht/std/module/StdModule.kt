package ru.DmN.pht.std.module

import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.base.unparsers.NUDefault
import ru.DmN.pht.std.module.compiler.java.compilers.NCModule
import ru.DmN.pht.std.module.parsers.NPModule

object StdModule : Module("std/module", true) {
    init {
        add("module", NPModule, NUDefault, NCModule)
    }
}