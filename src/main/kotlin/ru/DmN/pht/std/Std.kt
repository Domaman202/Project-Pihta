package ru.DmN.pht.std

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.lexer.Lexer
import ru.DmN.pht.base.parser.ast.NodeNodesList
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.unparsers.NUNodesList
import ru.DmN.pht.std.compiler.java.compilers.*
import ru.DmN.pht.std.parsers.*
import ru.DmN.pht.std.unparsers.*
import ru.DmN.pht.std.utils.Module

object Std : Module("std") {
    init {
        // Use
//        add("use",      NPDefault,  NUDefault,  NCUse)
        // Определить Макрос / Вставить Аргумент / Макрос
        add("defmacro", NPDefault,  NUDefMacro, NCDefMacro)
        add("macro-arg",NPDefault,  NUDefault,  NCMacroArg)
        add("macro_",   NPMacro,    NUMacro,    NCMacroB)
        add("macro",    NPDefault,  NUDefault,  NCMacroA)
        // Аннотации
        add("@abstract",NPDefault,NUDefault,NCAnnotation)
        add("@final",   NPDefault,NUDefault,NCAnnotation)
        add("@generic", NPDefault,NUDefault,NCGeneric)
        add("@override",NPDefault,NUDefault,NCAnnotation)
        add("@static",  NPDefault,NUDefault,NCAnnotation)
        add("@varargs", NPDefault,NUDefault,NCAnnotation)
        // Импорт
        add("import-macro",     NPDefault,  NUDefault,  NCImportMacro)
        add("import-extend",    NPDefault,  NUDefault,  NCImportExtends)
        add("import",           NPDefault,  NUDefault,  NCImport)
        // Пространство Имён
        add("ns",   NPNamespace,NUNamespace,NCNamespace)
        // Перечисление / Объект / Класс / Интерфейс
        add("enum", NPDefault,  NUDefault,  NCEnum)
        add("obj",  NPDefault,  NUDefault,  NCClass)
        add("cls",  NPDefault,  NUDefault,  NCClass)
        add("intf", NPDefault,  NUDefault,  NCClass)
        // Конструктор Enum-а / Конструктор / Расширение / Перегрузка / Абстрактная Функция / Функция / Лямбда
        add("ector",NPDefault,  NUDefault,  NCEnumCtor)
        add("efn",  NPDefault,  NUDefault,  NCExFunction)
        add("fn",   NPDefault,  NUDefault,  NCFunction)
        // Циклы
        add("repeat",   NPDefault,  NUDefault,  NCRepeat)
        add("while",    NPDefault,  NUDefault,  NCWhile)
        // Условия
        add("if",       NPDefault,  NUDefault,  NCIf)
        // Выход
        add("return",   NPDefault,  NUDefault,  NCReturn)
        add("yield",    NPDefault,  NUDefault,  NCYield)
        // Тело
        add("body",     NPDefault,  NUDefault,  NCBody)
        // Создать / Выполнить / Вызвать Супер-Конструктор / Вызвать
        add("new",      NPDefault,  NUDefault,  NCNew)
        add("ccall",    NPDefault,  NUDefault,  NCCtorCall)
        add("tcall",    NPDefault,  NUDefault,  NCThisCall)
        add("mcall_",   NPMethodCallB)
        add("mcall",    NPDefault,  NUDefault,  NCMethodCall)
        // Сеттеры
        add("fset_",    NPFieldSet, NUFieldSet, NCSetB)
        add("set_",     NPSet,      NUSet,      NCSetB)
        add("set",      NPDefault,  NUDefault,  NCSetA)
        // Геттеры
        add("fget_",                    compiler =  NCFieldGetB)
        add("fget",         NPDefault,  NUDefault,  NCFieldGetA)
        add("get_",         NPGet,      NUGetOrName,NCGetB)
        add("get",          NPDefault,  NUDefault,  NCGetA)
        add("get-or-name",  NPGetOrName,NUGetOrName,NCGetOrName)
        // Поля / Переменные
        add("efield",       NPDefault,  NUDefault,  NCEnumField)
        add("field",        NPDefault,  NUDefault,  NCField)
        add("def",          NPDefault,  NUDefault,  NCDef)
        // Преобразование типов
        add("cast",         NPDefault,  NUDefault,  NCCast)
        // Итераторы
        add("until",        NPDefault,  NUDefault) // todo:?
        add("range",        NPDefault,  NUDefault,  NCRange)
        // Значения
        add("valn",         NPValn,     NUNodesList,    NCValn)
        add("value_",       NPValueB)
        add("value",        NPValueA,   NUValue,        NCValue)
        // Пустой блок
        add("unit",         NPDefault,  NUDefault,      NCUnit)
    }

    override fun inject(compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val flag = !compiler.modules.contains(this)
        if (flag) {
            super.inject(compiler, ctx, ret)
            compiler.compile("""(
                (use std)
                (ns pht.std
                    (defmacro ctor [args body] (fn <init> ^void (macro-arg args) (macro-arg body)))
                )
            )""".trimIndent(), ctx)
        }
        return if (ctx.type.method) {
            val variable = ctx.body!!.addVariable("std", "ru.DmN.pht.std.StdFunctions", tmp = ret)
            val label = Label()
            ctx.method!!.node.run {
                visitLabel(label)
                visitFieldInsn(
                    Opcodes.GETSTATIC,
                    "ru/DmN/pht/std/StdFunctions",
                    "INSTANCE",
                    "Lru/DmN/pht/std/StdFunctions;"
                )
                if (ret)
                    variable
                else {
                    visitVarInsn(Opcodes.ASTORE, variable.id)
                    ctx.method.variableStarts[variable.id] = label
                    null
                }
            }
        } else null
    }
}