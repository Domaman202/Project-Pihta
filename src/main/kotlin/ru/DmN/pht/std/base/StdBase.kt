package ru.DmN.pht.std.base

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.Base
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.unparser.unparsers.NUNodesList
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.compilers.*
import ru.DmN.pht.std.base.compiler.java.ctx.GlobalContext
import ru.DmN.pht.std.base.compiler.java.utils.*
import ru.DmN.pht.std.base.parsers.*
import ru.DmN.pht.std.base.unparsers.*

object StdBase : Module("std/base") {
    init {
        // Use
        add("use",          NPDefault,  NUDefault,  NCUse)
        // Определить Макрос / Вставить Аргумент / Макрос
        add("defmacro",     NPMacroDef, NUMacroDef, NCDefMacro)
        add("macro-unroll", NPDefault,  NUDefault,  NCMacroUnroll)
        add("macro-arg",    NPMacroArg, NUMacroArg, NCMacroArg)
        add("macro-name",   NPMacroArg, NUMacroArg, NCMacroName)
        add("macro!",       NPMacro,    NUMacro,    NCMacro)
        // Аннотации
        add("@abstract",NPDefault,NUDefault, NCAnnotation)
        add("@final",   NPDefault,NUDefault, NCAnnotation)
        add("@generic", NPDefault,NUDefault,NCGeneric)
        add("@override",NPDefault,NUDefault, NCAnnotation)
        add("@static",  NPDefault,NUDefault, NCAnnotation)
        add("@varargs", NPDefault,NUDefault, NCAnnotation)
        // Импорт
        add("import-macro",     NPDefault,  NUDefault,  NCImportMacro)
        add("import-extend",    NPDefault,  NUDefault,  NCImportExtends)
        add("import",           NPDefault,  NUDefault,  NCImport)
        // Пространство Имён
        add("ns",   NPNs,       NUNs,       NCNs)
        // Объект / Класс / Интерфейс
        add("obj",  NPDefault,  NUDefault,  NCClass)
        add("cls",  NPDefault,  NUDefault,  NCClass)
        add("intf", NPDefault,  NUDefault,  NCClass)
        // Расширение / Функция
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
        add("mcall!",   NPMethodCallB)
        add("mcall",    NPDefault,  NUDefault,  NCMethodCallA)
        // Сеттеры
        add("fset!",    NPFieldSet, NUFieldSet, NCSetB)
        add("set!",     NPSet,      NUSet,      NCSetB)
        add("set",      NPDefault,  NUDefault,  NCSetA)
        // Геттеры
        add("fget!",                    compiler =  NCFieldGetB)
        add("fget",         NPDefault,  NUDefault,  NCFieldGetA)
        add("get!",         NPGet,      NUGetOrName,NCGetB)
        add("get",          NPDefault,  NUDefault,  NCGetA)
        add("get-or-name",  NPGetOrName,NUGetOrName,NCGetOrName)
        // Поле / Переменная
        add("field",        NPDefault,  NUDefault,  NCField)
        add("def",          NPDefault,  NUDefault,  NCDef)
        // Преобразование, Проверка, Получение типов
        add("as",           NPDefault,  NUDefault,  NCAs)
        add("is",           NPDefault,  NUDefault,  NCIs)
        add("typeof",       NPDefault,  NUDefault,  NCTypeof)
        // Compile-Time значения
        add("ns-name",      NPDefault,  NUDefault,  NCNsName)
        // Значения
        add("valn",         NPValn,     NUNodesList,    NCValn)
        add("value!",       NPValueB)
        add("value",        NPValueA,   NUValue,        NCValue)
        // Пустой блок
        add("unit",         NPDefault,  NUDefault,      NCUnit)
    }

    override fun inject(compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (!ctx.modules.contains(this)) {
            super.inject(compiler, ctx, ret)
            ctx.contexts["std/global"] = GlobalContext()
            compiler.contexts["std/macros"] = HashMap<String, MutableList<MacroDefine>>()
            compiler.compile(String(StdBase::class.java.getResourceAsStream("/pht/std/module.pht")!!.readAllBytes()), ParsingContext(mutableListOf(Base)), ctx)
        }
        return if (ctx.isMethod() && ctx.isBody()) {
            val variable = ctx.body.addVariable("std", "ru.DmN.pht.std.StdFunctions", tmp = ret)
            val label = Label()
            ctx.method.node.run {
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