package ru.DmN.pht.std.base

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.unparser.UnparsingContext
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.base.utils.Variable
import ru.DmN.pht.std.base.compiler.java.compilers.*
import ru.DmN.pht.std.base.compiler.java.ctx.GlobalContext
import ru.DmN.pht.std.base.compiler.java.utils.body
import ru.DmN.pht.std.base.compiler.java.utils.isBody
import ru.DmN.pht.std.base.compiler.java.utils.isMethod
import ru.DmN.pht.std.base.compiler.java.utils.method
import ru.DmN.pht.std.base.parsers.*
import ru.DmN.pht.std.base.unparsers.NUDefault
import ru.DmN.pht.std.base.unparsers.NUFieldSet
import ru.DmN.pht.std.base.unparsers.NUGetOrName
import ru.DmN.pht.std.base.unparsers.NUSet
import ru.DmN.pht.std.value.StdValue

object StdBase : Module("std/base") {
    init {
        // Импорт
        add("import-extends",   NPDefault,  NUDefault,  NCImportExtends)
        add("import",           NPDefault,  NUDefault,  NCImport)
        // Пространство Имён
        add("ns",       NPDefault,  NUDefault,  NCNewNs)
        add("sub-ns",   NPDefault,  NUDefault,  NCSubNs)
        // Аннотации
        add("@abstract",NPDefault,  NUDefault,  NCAnnotation)
        add("@bridge",  NPDefault,  NUDefault,  NCAnnotation)
        add("@final",   NPDefault,  NUDefault,  NCAnnotation)
        add("@generic", NPDefault,  NUDefault,  NCGeneric)
        add("@override",NPDefault,  NUDefault,  NCAnnotation)
        add("@static",  NPDefault,  NUDefault,  NCAnnotation)
        add("@varargs", NPDefault,  NUDefault,  NCAnnotation)
        // Объект / Класс / Интерфейс
        add("obj",  NPDefault,  NUDefault,  NCClass)
        add("cls",  NPDefault,  NUDefault,  NCClass)
        add("itf",  NPDefault,  NUDefault,  NCClass)
        // Расширение / Функция
        add("efn",  NPDefault,  NUDefault,  NCExFn)
        add("bfn",  NPDefault,  NUDefault,  NCBridgeFn)
        add("defn", NPDefault,  NUDefault,  NCDefn)
        add("rfn",  NPDefault,  NUDefault,  NCRfn)
        add("fn",   NPDefault,  NUDefault,  NCFn)
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
        add("set!",     NPSetB,     NUSet,      NCSetB)
        add("set",      NPSetA,     NUDefault,  NCSetA)
        // Геттеры
        add("fget!",                    compiler =  NCFieldGetB)
        add("fget",         NPDefault,  NUDefault,  NCFieldGetA)
        add("get!",         NPGet,      NUGetOrName,NCGetB)
        add("get",          NPDefault,  NUDefault,  NCGetA)
        // Поле / Переменная
        add("field",        NPDefault,  NUDefault,  NCField)
        add("def",          NPDefault,  NUDefault,  NCDef)
        // Преобразование, Проверка, Получение типов
        add("as",           NPDefault,  NUDefault,  NCAs)
        add("is",           NPDefault,  NUDefault,  NCIs)
        add("typeof",       NPDefault,  NUDefault,  NCTypeof)
        // Пустой блок
        add("unit",         NPDefault,  NUDefault,  NCUnit)
    }

    override fun inject(parser: Parser, ctx: ParsingContext) {
        if (!ctx.loadedModules.contains(this)) {
            super.inject(parser, ctx)
            StdValue.inject(parser, ctx)
        }
    }

    override fun inject(unparser: Unparser, ctx: UnparsingContext) {
        if (!ctx.modules.contains(this)) {
            super.inject(unparser, ctx)
            StdValue.inject(unparser, ctx)
        }
    }

    override fun inject(compiler: Compiler, ctx: CompilationContext, ret: Boolean): Variable? {
        if (!ctx.loadedModules.contains(this)) {
            StdValue.inject(compiler, ctx, ret)
            ctx.contexts["std/base/global"] = GlobalContext()
            super.inject(compiler, ctx, ret)
        }
        return if (ctx.isMethod() && ctx.isBody()) {
            val variable = ctx.body.addVariable("std", "ru.DmN.pht.std.base.StdFunctions", tmp = ret)
            val label = Label()
            ctx.method.node.run {
                visitLabel(label)
                visitFieldInsn(
                    Opcodes.GETSTATIC,
                    "ru/DmN/pht/std/base/StdFunctions",
                    "INSTANCE",
                    "Lru/DmN/pht/std/base/StdFunctions;"
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