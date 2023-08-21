package ru.DmN.pht.std.enums

import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.compiler.java.compilers.NCEnum
import ru.DmN.pht.std.compiler.java.compilers.NCEnumCtor
import ru.DmN.pht.std.compiler.java.compilers.NCEnumField
import ru.DmN.pht.std.unparsers.NUDefault

object StdEnums : Module("std/enums") {
    init {
        add("enum",     NPDefault,  NUDefault,  NCEnum)
        add("ector",    NPDefault,  NUDefault,  NCEnumCtor)
        add("efield",   NPDefault,  NUDefault,  NCEnumField)
    }
}