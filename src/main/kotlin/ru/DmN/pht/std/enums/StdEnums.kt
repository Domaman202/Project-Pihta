package ru.DmN.pht.std.enums

import ru.DmN.pht.std.base.ups.UPDefault
import ru.DmN.pht.std.base.utils.StdModule
import ru.DmN.pht.std.enums.compiler.java.compilers.NCEnum
import ru.DmN.pht.std.enums.compiler.java.compilers.NCEnumCtor
import ru.DmN.pht.std.enums.compiler.java.compilers.NCEnumField

object StdEnums : StdModule("std/enums") {
    init {
        add("enum",     UPDefault,  NCEnum)
        add("ector",    UPDefault,  NCEnumCtor)
        add("efield",   UPDefault,  NCEnumField)
    }
}