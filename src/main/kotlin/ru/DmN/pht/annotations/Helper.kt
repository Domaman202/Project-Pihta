package ru.DmN.pht.annotations

import ru.DmN.pht.helper.parsers.NPArgument
import ru.DmN.pht.helper.parsers.NPValue
import ru.DmN.pht.imports.parsers.NPValueList
import ru.DmN.siberia.utils.Module

object Helper : Module("pht/annotations/helper") {
    init {
        add(Regex("retention|target|inherited"), NPArgument)
        add(Regex("valn!"),  NPValueList)
        add(Regex("value!"), NPValue)
    }
}