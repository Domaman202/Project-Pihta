package ru.DmN.pht.std.util

import ru.DmN.pht.std.base.compiler.java.utils.clazz
import ru.DmN.pht.std.base.compiler.java.utils.global
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.std.base.ups.UPDefault
import ru.DmN.pht.std.base.utils.StdModule
import ru.DmN.pht.std.util.compiler.java.compilers.*
import ru.DmN.pht.std.util.parsers.NPLazySymbol

object StdUtil : StdModule("std/util") {
    init {
        // Compile-Time значения
        add("valn-repeat",      UPDefault,      NCRepeatExpr)
        add("class-of-symbol",  UPDefault,      NCClass)
        add("rand-symbol",      UPDefault,      NCRandSymbol)
        add("lazy-symbol",      NPLazySymbol,   UPDefault,  UPDefault,  NCLazySymbol)
        add("symbol",       UPDefault,  NCSymbol)
        add("*cls-name*",   UPDefault,  NCCompileTimeName { _, ctx -> ctx.clazz.clazz.name })
        add("*fn-name*",    UPDefault,  NCCompileTimeName { _, ctx -> ctx.method.method.name })
        add("*ns-name*",    UPDefault,  NCCompileTimeName { _, ctx -> ctx.global.namespace })
    }
}