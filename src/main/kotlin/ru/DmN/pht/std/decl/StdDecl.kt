package ru.DmN.pht.std.decl

import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.base.unparsers.NUDefault
import ru.DmN.pht.std.decl.compiler.java.compilers.NCClass
import ru.DmN.pht.std.decl.compiler.java.compilers.NCField
import ru.DmN.pht.std.decl.compiler.java.compilers.NCFn

object StdDecl : Module("std/decl") {
    init {
        add("cls",  NPDefault,  NUDefault,  NCClass)
        add("defn",   NPDefault,  NUDefault,  NCFn)
        add("field",NPDefault,  NUDefault,  NCField)
    }
}