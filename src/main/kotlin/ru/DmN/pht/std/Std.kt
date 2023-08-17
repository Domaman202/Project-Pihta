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
        add(name = "use",           parser = NPDefault,             unparser = NUDefault,   compiler = NCUse)
        // Определить Макрос / Вставить Аргумент / Макрос
        add(name = "defmacro",      parser = NPDefault,             unparser = NUDefMacro,  compiler = NCDefMacro)
        add(name = "macro-arg",     parser = NPDefault,             unparser = NUDefault,   compiler = NCMacroArg)
        add(name = "macro_",        parser = NPMacro,               unparser = NUMacro,     compiler = NCMacroB)
        add(name = "macro",         parser = NPDefault,             unparser = NUDefault,   compiler = NCMacroA)
        // Аннотации
        add(name = "@abstract",     parser = NPDefault,             unparser = NUDefault,   compiler = NCAnnotation)
        add(name = "@final",        parser = NPDefault,             unparser = NUDefault,   compiler = NCAnnotation)
        add(name = "@generic",      parser = NPDefault,             unparser = NUDefault,   compiler = NCGeneric)
        add(name = "@override",     parser = NPDefault,             unparser = NUDefault,   compiler = NCAnnotation)
        add(name = "@static",       parser = NPDefault,             unparser = NUDefault,   compiler = NCAnnotation)
        add(name = "@varargs",      parser = NPDefault,             unparser = NUDefault,   compiler = NCAnnotation)
        // Импорт
        add(name = "eimport",       parser = NPDefault,             unparser = NUDefault,   compiler = NCExtendsImport)
        add(name = "import",        parser = NPDefault,             unparser = NUDefault,   compiler = NCImport)
        // Пространство Имён
        add(name = "ns",            parser = NPNamespace,           unparser = NUNamespace, compiler = NCNamespace)
        // Перечисление / Объект / Класс / Интерфейс
        add(name = "enum",          parser = NPDefault,             unparser = NUDefault,   compiler = NCEnum)
        add(name = "obj",           parser = NPDefault,             unparser = NUDefault,   compiler = NCClass)
        add(name = "cls",           parser = NPDefault,             unparser = NUDefault,   compiler = NCClass)
        add(name = "intf",          parser = NPDefault,             unparser = NUDefault,   compiler = NCClass)
        // Конструктор Enum-а / Конструктор / Расширение / Перегрузка / Абстрактная Функция / Функция / Лямбда
        add(name = "ector",         parser = NPDefault,             unparser = NUDefault,   compiler = NCEnumCtor)
        add(name = "efn",           parser = NPDefault,             unparser = NUDefault,   compiler = NCExFunction)
        add(name = "fn",            parser = NPDefault,             unparser = NUDefault,   compiler = NCFunction)
        // Циклы
        add(name = "repeat",        parser = NPDefault,             unparser = NUDefault,   compiler = NCRepeat)
        add(name = "while",         parser = NPDefault,             unparser = NUDefault,   compiler = NCWhile)
        // Условия
        add(name = "if",            parser = NPDefault,             unparser = NUDefault,   compiler = NCIf)
        // Выход
        add(name = "return",        parser = NPDefault,             unparser = NUDefault,   compiler = NCReturn)
        add(name = "yield",         parser = NPDefault,             unparser = NUDefault,   compiler = NCYield)
        // Тело
        add(name = "body",          parser = NPDefault,             unparser = NUDefault,   compiler = NCBody)
        // Создать / Выполнить / Вызвать Супер-Конструктор / Вызвать
        add(name = "new",           parser = NPDefault,             unparser = NUDefault,   compiler = NCNew)
        add(name = "ccall",         parser = NPDefault,             unparser = NUDefault,   compiler = NCCtorCall)
        add(name = "tcall",         parser = NPDefault,             unparser = NUDefault,   compiler = NCThisCall)
        add(name = "mcall_",        parser = NPMethodCallB)
        add(name = "mcall",         parser = NPDefault,             unparser = NUDefault,   compiler = NCMethodCall)
        // Сеттеры
        add(name = "fset_",         parser = NPFieldSet,            unparser = NUFieldSet,  compiler = NCSetB)
        add(name = "set_",          parser = NPSet,                 unparser = NUSet,       compiler = NCSetB)
        add(name = "set",           parser = NPDefault,             unparser = NUDefault,   compiler = NCSetA)
        // Геттеры
        add(name = "fget_",                                                                 compiler = NCFieldGetB)
        add(name = "fget",          parser = NPDefault,             unparser = NUDefault,   compiler = NCFieldGetA)
        add(name = "get_",          parser = NPGet,                 unparser = NUGetOrName, compiler = NCGetB)
        add(name = "get",           parser = NPDefault,             unparser = NUDefault,   compiler = NCGetA)
        add(name = "get-or-name",   parser = NPGetOrName,           unparser = NUGetOrName, compiler = NCGetOrName)
        // Поля / Переменные
        add(name=  "efield",        parser = NPDefault,             unparser = NUDefault,   compiler = NCEnumField)
        add(name = "field",         parser = NPDefault,             unparser = NUDefault,   compiler = NCField)
        add(name = "def",           parser = NPDefault,             unparser = NUDefault,   compiler = NCDef)
        // Преобразование типов
        add(name = "cast",          parser = NPDefault,             unparser = NUDefault,   compiler = NCCast)
        // Итераторы
        add(name = "until",         parser = NPDefault,             unparser = NUDefault)
        add(name = "range",         parser = NPDefault,             unparser = NUDefault,   compiler = NCRange)
        // Значения
        add(name = "valn",          parser = NPValn,                unparser = NUNodesList, compiler = NCValn)
        add(name = "value_",        parser = NPValueB)
        add(name = "value",         parser = NPValueA,              unparser = NUValue,     compiler = NCValue)
        // Пустой блок
        add(name = "unit",          parser = NPDefault,             unparser = NUDefault,   compiler = NCUnit)
    }

    override fun inject(compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        val flag = !compiler.modules.contains(this)
        if (flag) {
            super.inject(compiler, ctx, ret)
            Parser(Lexer("""((use std)
                (defmacro ctor [args body] (fn <init> ^void (macro-arg args) (macro-arg body)))
            )""".trimIndent())).parseNode()!!.nodes.forEach {
                if (it.tkOperation.text == "defmacro") {
                    NCDefMacro.compile(it as NodeNodesList, compiler, ctx, false)
                }
            }
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