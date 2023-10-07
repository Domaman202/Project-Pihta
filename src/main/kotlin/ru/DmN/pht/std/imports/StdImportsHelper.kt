package ru.DmN.pht.std.imports

import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.imports.parsers.NPArgument
import ru.DmN.pht.std.imports.parsers.NPValue
import ru.DmN.pht.std.imports.parsers.NPValueList

object StdImportsHelper : Module("std/imports/helper") {
    init {
        add("type|extend|macro".toRegex(), NPArgument)
        add("valn!",    NPValueList)
        add("value!",   NPValue)
    }
}