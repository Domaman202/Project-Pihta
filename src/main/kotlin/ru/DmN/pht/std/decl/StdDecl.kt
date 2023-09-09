package ru.DmN.pht.std.decl

import ru.DmN.pht.std.base.ups.UPDefault
import ru.DmN.pht.std.base.utils.StdModule
import ru.DmN.pht.std.decl.compiler.java.compilers.NCClass
import ru.DmN.pht.std.decl.compiler.java.compilers.NCField
import ru.DmN.pht.std.decl.compiler.java.compilers.NCFn

object StdDecl : StdModule("std/decl") {
    init {
        add("cls",  UPDefault,  NCClass)
        add("defn", UPDefault,  NCFn)
        add("field",UPDefault,  NCField)
    }
}