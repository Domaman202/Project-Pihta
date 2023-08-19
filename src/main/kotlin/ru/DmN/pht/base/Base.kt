package ru.DmN.pht.base

import ru.DmN.pht.base.compiler.java.compilers.NCDefault
import ru.DmN.pht.base.compiler.java.compilers.NCUse
import ru.DmN.pht.base.parser.parsers.NPNodesList
import ru.DmN.pht.base.parser.parsers.NPUse
import ru.DmN.pht.base.unparser.unparsers.NUNodesList
import ru.DmN.pht.base.unparser.unparsers.NUUse
import ru.DmN.pht.std.utils.Module

object Base : Module("base") {
    init {
        add("use!", NPUse,          NUUse,      NCUse)
        add("use",  NPUse,          NUUse,      NCUse) // overrides by std
        add("progn",NPNodesList,    NUNodesList,NCDefault)
    }
}