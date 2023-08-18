package ru.DmN.pht.std.util

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.Std
import ru.DmN.pht.std.util.compiler.java.compilers.NCFor
import ru.DmN.pht.std.unparsers.NUDefault
import ru.DmN.pht.std.utils.Module

object StdUtil : Module("std/util") {
    init {
        add("for", NPDefault, NUDefault, NCFor)
    }

    override fun inject(compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (!compiler.modules.contains(this)) {
            super.inject(compiler, ctx, ret)
            compiler.compile(String(Std::class.java.getResourceAsStream("/pht/std/util/module.pht")!!.readAllBytes()), ctx)
        }
        return null
    }
}