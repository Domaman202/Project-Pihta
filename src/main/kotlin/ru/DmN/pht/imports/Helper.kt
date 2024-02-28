package ru.DmN.pht.imports

import ru.DmN.pht.helper.parsers.NPArgument
import ru.DmN.pht.helper.parsers.NPValue
import ru.DmN.pht.imports.parsers.NPValueList
import ru.DmN.pht.module.utils.Module

object Helper : Module("pht/imports/helper") {
    init {
        add(Regex("types|extensions|macros|methods"), NPArgument)
        add(Regex("valn!"),  NPValueList)
        add(Regex("value!"), NPValue)
    }
}