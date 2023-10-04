package ru.DmN.pht.std.enums

import ru.DmN.pht.std.base.utils.StdModule
import ru.DmN.pht.std.enums.ups.NUPEField
import ru.DmN.pht.std.enums.ups.NUPEnum
import ru.DmN.pht.std.oop.ups.NUPCtor

object StdEnums : StdModule("std/enums") {
    init {
        add("enum",     NUPEnum)
        add("ector",    NUPCtor)
        add("efield",   NUPEField)
    }
}