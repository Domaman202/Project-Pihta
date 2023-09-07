package ru.DmN.pht.std.util

import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.base.compiler.java.utils.clazz
import ru.DmN.pht.std.base.compiler.java.utils.global
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.std.base.unparsers.NUDefault
import ru.DmN.pht.std.util.compiler.java.compilers.*
import ru.DmN.pht.std.util.parsers.NPLazySymbol

object StdUtil : Module("std/util") {
    init {
        // Compile-Time значения
        add("valn-repeat",      NPDefault,  NUDefault,  NCRepeatExpr)
        add("class-of-symbol",  NPDefault,  NUDefault,  NCClass)
        add("rand-symbol",  NPDefault,      NUDefault,  NCRandSymbol)
        add("lazy-symbol",  NPLazySymbol,   NUDefault,  NCLazySymbol)
        add("symbol",       NPDefault,  NUDefault,  NCSymbol)
        add("*cls-name*",   NPDefault,  NUDefault,  NCCompileTimeName { _, ctx -> ctx.clazz.clazz.name })
        add("*fn-name*",    NPDefault,  NUDefault,  NCCompileTimeName { _, ctx -> ctx.method.method.name })
        add("*ns-name*",    NPDefault,  NUDefault,  NCCompileTimeName { _, ctx -> ctx.global.namespace })
    }
}