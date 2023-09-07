package ru.DmN.pht.std.macro

import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.compilers.NCImportMacro
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
        // Импорт
        add("import-macro",     NPDefault,      NUDefault,  NCImportMacro)
        // Определение
        add("defmacro",         NPMacroDef,     NUMacroDef, NCDefMacro)
        // Развёртка / Встраивание
        add("macro-unroll",     NPMacroUnroll,  NUDefault,  NCMacroUnroll)
        add("macro-inline",     NPDefault,      NUDefault,  NCMacroInline)
        add("macro-arg",        NPMacroArg,     NUMacroArg, NCMacroArg)
        add("macro-name",       NPMacroArg,     NUMacroArg, NCMacroName)
        // Количество аргументов
        add("macro-arg-count",  NPMacroArg,     NUMacroArg, NCMacroArgCount)
        // Переменная-макро-аргумент
        add("macro-var",        NPMacroVar,     NUMacroVar, NCMacroVar)
        // Использование
        add("macro!",           NPMacro,        NUMacro,    NCMacro)
    }

    override fun inject(compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (!ctx.loadedModules.contains(this)) {
            super.inject(compiler, ctx, ret)
            compiler.contexts.getOrPut("std/base/macros") { HashMap<String, MutableList<MacroDefine>>() }
        }
        return null
    }
}

/**
 Idea's
 А что если разбить парсинг на несколько этапов?
 1. Просто парсинг
 2. Обработка
 Таким образом сначала мы парсим все выражения (+- правильно)
 Потом мы их обрабатываем: вставляем макросы и вычисляем нужные детали
 */