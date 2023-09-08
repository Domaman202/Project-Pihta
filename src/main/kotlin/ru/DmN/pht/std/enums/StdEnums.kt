package ru.DmN.pht.std.enums

import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.processor.processors.NRDefault
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.base.unparsers.NUDefault
import ru.DmN.pht.std.enums.compiler.java.compilers.NCEnum
import ru.DmN.pht.std.enums.compiler.java.compilers.NCEnumCtor
import ru.DmN.pht.std.enums.compiler.java.compilers.NCEnumField

object StdEnums : Module("std/enums") {
    init {
        add("enum",     NPDefault,  NUDefault,  NRDefault,  NCEnum)
        add("ector",    NPDefault,  NUDefault,  NRDefault,  NCEnumCtor)
        add("efield",   NPDefault,  NUDefault,  NRDefault,  NCEnumField)
    }
}