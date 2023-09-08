package ru.DmN.pht.std.util

import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.processor.processors.NRDefault
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
        add("valn-repeat",      NPDefault,  NUDefault,  NRDefault,  NCRepeatExpr)
        add("class-of-symbol",  NPDefault,  NUDefault,  NRDefault,  NCClass)
        add("rand-symbol",  NPDefault,      NUDefault,  NRDefault,  NCRandSymbol)
        add("lazy-symbol",  NPLazySymbol,   NUDefault,  NRDefault,  NCLazySymbol)
        add("symbol",       NPDefault,  NUDefault,  NRDefault,  NCSymbol)
        add("*cls-name*",   NPDefault,  NUDefault,  NRDefault,  NCCompileTimeName { _, ctx -> ctx.clazz.clazz.name })
        add("*fn-name*",    NPDefault,  NUDefault,  NRDefault,  NCCompileTimeName { _, ctx -> ctx.method.method.name })
        add("*ns-name*",    NPDefault,  NUDefault,  NRDefault,  NCCompileTimeName { _, ctx -> ctx.global.namespace })
    }
}