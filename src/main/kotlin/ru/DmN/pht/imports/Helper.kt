package ru.DmN.pht.std.imports

import ru.DmN.pht.std.imports.parsers.NPArgument
import ru.DmN.pht.std.imports.parsers.NPValue
import ru.DmN.pht.std.imports.parsers.NPValueList
import ru.DmN.siberia.utils.Module

object Helper : Module("pht/imports/helper") {
    init {
        add(Regex("types|extensions|macros|methods"), NPArgument)
        add(Regex("valn!"),  NPValueList)
        add(Regex("value!"), NPValue)
    }
}