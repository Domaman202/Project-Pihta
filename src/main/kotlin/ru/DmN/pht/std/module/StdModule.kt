package ru.DmN.pht.std.module

import ru.DmN.pht.base.compiler.java.compilers.NCDefault
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.base.unparsers.NUDefault
import ru.DmN.pht.std.module.parsers.NPModule

object StdModule : Module("std/module") {
    init {
        add("module", NPModule, NUDefault, NCDefault)
    }
}