package ru.DmN.pht.std.imports

import ru.DmN.siberia.utils.Module
import ru.DmN.pht.std.imports.parsers.NPArgument
import ru.DmN.pht.std.imports.parsers.NPValue
import ru.DmN.pht.std.imports.parsers.NPValueList

object StdImportsHelper : Module("pht/imports/helper") {
    init {
        add("types|extends|macros".toRegex(), NPArgument)
        add("valn!",    NPValueList)
        add("value!",   NPValue)
    }
}