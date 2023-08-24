package ru.DmN.pht.std.macro

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.utils.MacroDefine
import ru.DmN.pht.std.base.unparsers.*
import ru.DmN.pht.std.macro.compiler.java.compilers.*
import ru.DmN.pht.std.macro.parsers.*
import ru.DmN.pht.std.macro.unparsers.NUMacro
import ru.DmN.pht.std.macro.unparsers.NUMacroArg
import ru.DmN.pht.std.macro.unparsers.NUMacroDef
import ru.DmN.pht.std.macro.unparsers.NUMacroVar

object StdMacro : Module("std/macro") {
    init {
        add("defmacro",         NPMacroDef,     NUMacroDef, NCDefMacro)
        add("macro-unroll",     NPMacroUnroll,  NUDefault,  NCMacroUnroll)
        add("macro-inline",     NPDefault,      NUDefault,  NCMacroInline)
        add("macro-arg-count",  NPMacroArg,     NUMacroArg, NCMacroArgCount)
        add("macro-arg",        NPMacroArg,     NUMacroArg, NCMacroArg)
        add("macro-name",       NPMacroArg,     NUMacroArg, NCMacroName)
        add("macro-var",        NPMacroVar,     NUMacroVar, NCMacroVar)
        add("macro!",           NPMacro,        NUMacro,    NCMacro)
    }

    override fun inject(compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (!ctx.modules.contains(this)) {
            super.inject(compiler, ctx, ret)
            compiler.contexts["std/base/macros"] = HashMap<String, MutableList<MacroDefine>>()
        }
        return null
    }
}