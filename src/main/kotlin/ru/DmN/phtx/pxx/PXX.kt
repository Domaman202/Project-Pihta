package ru.DmN.phtx.pxx

import ru.DmN.pht.std.utils.StdModule
import ru.DmN.phtx.pxx.ups.NUPAlias

object PXX : StdModule("phtx/pxx") {
    init {
        // б
        "боле" to "great"
        "боле-иль-равно" to "great-or-eq"
        // в
        "величить" to "inc"
        "воздать" to "ret"
        // д
        "династия" to "cls"
        // к
        "коли" to "if"
        // м
        "меньшить" to "dec"
        "молвить" to "println"
        // н
        "не-боле" to "less"
        "ничто" to "nil"
        // п
        "познать" to "def"
        "покуда" to "cycle"
        // с
        "становить" to "set"
        // ц
        "царь-батюшка-главный" to "app-fn"
    }

    infix fun String.to(alias: String) {
        add(this, NUPAlias(alias))
    }
}