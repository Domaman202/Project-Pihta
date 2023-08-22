package ru.DmN.pht.std.util

import ru.DmN.pht.base.Base
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.StdBase
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.base.compiler.java.utils.clazz
import ru.DmN.pht.std.base.compiler.java.utils.global
import ru.DmN.pht.std.base.compiler.java.utils.macro
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.std.base.unparsers.NUDefault
import ru.DmN.pht.std.math.StdMath
import ru.DmN.pht.std.util.compiler.java.compilers.NCCompileTimeName
import ru.DmN.pht.std.util.compiler.java.compilers.NCSymbol

object StdUtil : Module("std/util") {
    init {
        // Compile-Time значения
        add("symbol",       NPDefault,  NUDefault,  NCSymbol)
        add("*cls-name*",   NPDefault,  NUDefault,  NCCompileTimeName { _, ctx -> ctx.clazz.clazz.name })
        add("*fn-name*",    NPDefault,  NUDefault,  NCCompileTimeName { _, ctx -> ctx.method.method.name })
        add("*ns-name*",    NPDefault,  NUDefault,  NCCompileTimeName { _, ctx -> ctx.global.namespace })
    }

    override fun inject(compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (!ctx.modules.contains(this)) {
            super.inject(compiler, ctx, ret)
            compiler.compile(
                String(StdBase::class.java.getResourceAsStream("/pht/std/util/module.pht")!!.readAllBytes()),
                ParsingContext(mutableListOf(Base, StdBase, StdMath)),
                CompilationContext(mutableListOf(Base, StdBase, StdMath), ctx.contexts)
            )
        }
        return null
    }
}