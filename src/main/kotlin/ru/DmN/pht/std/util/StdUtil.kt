package ru.DmN.pht.std.util

import ru.DmN.pht.base.Base
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.Std
import ru.DmN.pht.std.utils.Module

object StdUtil : Module("std/util") {
    override fun inject(compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (!ctx.modules.contains(this)) {
            super.inject(compiler, ctx, ret)
            compiler.compile(String(Std::class.java.getResourceAsStream("/pht/std/util/module.pht")!!.readAllBytes()), ParsingContext(mutableListOf(Base)), ctx)
        }
        return null
    }
}