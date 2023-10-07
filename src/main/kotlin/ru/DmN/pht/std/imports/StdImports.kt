package ru.DmN.pht.std.imports

import ru.DmN.pht.std.base.utils.StdModule
import ru.DmN.pht.std.imports.ups.NUPAliasType
import ru.DmN.pht.std.imports.ups.NUPImport

object StdImports : StdModule("std/imports") {
    init {
        add("import",       NUPImport)
        add("alias-type",   NUPAliasType)
    }
}