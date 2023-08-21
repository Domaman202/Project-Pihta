package ru.DmN.pht.std.enums

import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.base.unparsers.NUDefault
import ru.DmN.pht.std.enums.compiler.java.compilers.NCEnum
import ru.DmN.pht.std.enums.compiler.java.compilers.NCEnumCtor
import ru.DmN.pht.std.enums.compiler.java.compilers.NCEnumField

object StdEnums : Module("std/enums") {
    init {
        add("enum",     NPDefault,  NUDefault,  NCEnum)
        add("ector",    NPDefault,  NUDefault,  NCEnumCtor)
        add("efield",   NPDefault,  NUDefault,  NCEnumField)
    }
}