package ru.DmN.pht.std.base

import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Unparser
import ru.DmN.pht.base.compiler.java.Compiler
import ru.DmN.pht.base.compiler.java.ctx.CompilationContext
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parser.parsers.NPDefault
import ru.DmN.pht.base.processor.processors.NRDefault
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
import ru.DmN.pht.std.base.unparsers.NUFieldSet
import ru.DmN.pht.std.base.unparsers.NUGetOrName
import ru.DmN.pht.std.base.unparsers.NUSet
import ru.DmN.pht.std.base.ups.UPDefault
import ru.DmN.pht.std.base.utils.StdModule
import ru.DmN.pht.std.value.StdValue

object StdBase : StdModule("std/base") {
    init {
        // Импорт
        add("import-extends",   UPDefault,  NCImportExtends)
        add("import",           UPDefault,  NCImport)
        // Пространство Имён
        add("ns",       UPDefault,  NCNewNs)
        add("sub-ns",   UPDefault,  NCSubNs)
        // Аннотации
        add("@abstract",UPDefault,  NCAnnotation)
        add("@bridge",  UPDefault,  NCAnnotation)
        add("@final",   UPDefault,  NCAnnotation)
        add("@generic", UPDefault,  NCGeneric)
        add("@override",UPDefault,  NCAnnotation)
        add("@static",  UPDefault,  NCAnnotation)
        add("@varargs", UPDefault,  NCAnnotation)
        // Объект / Класс / Интерфейс
        add("obj",  UPDefault,  NCClass)
        add("cls",  UPDefault,  NCClass)
        add("itf",  UPDefault,  NCClass)
        // Расширение / Функция
        add("efn",  UPDefault,  NCExFn)
        add("bfn",  UPDefault,  NCBridgeFn)
        add("defn", UPDefault,  NCDefn)
        add("rfn",  UPDefault,  NCRfn)
        add("fn",   UPDefault,  NCFn)
        // Циклы
        add("repeat",   UPDefault,  NCRepeat)
        add("while",    UPDefault,  NCWhile)
        // Условия
        add("if",       UPDefault,  NCIf)
        // Выход
        add("return",   UPDefault,  NCReturn)
        add("yield",    UPDefault,  NCYield)
        // Тело
        add("body",     UPDefault,  NCBody)
        // Создать / Выполнить / Вызвать Супер-Конструктор / Вызвать
        add("new",      UPDefault,  NCNew)
        add("ccall",    UPDefault,  NCCtorCall)
        add("mcall!",   NPMethodCallB)
        add("mcall",    UPDefault,  UPDefault,  UPDefault,  NCMethodCallA)
        // Сеттеры
        add("fset!",    NPFieldSet, NUFieldSet, UPDefault,  NCSetB)
        add("set!",     NPSetB,     NUSet,      UPDefault,  NCSetB)
        add("set",      NPSetA,     UPDefault,  UPDefault,  NCSetA)
        // Геттеры
        add("fget!",        UPDefault,  NCFieldGetB)
        add("fget",         UPDefault,  NCFieldGetA)
        add("get!",         NPGet,      NUGetOrName,UPDefault,  NCGetB)
        add("get",          UPDefault,  NCGetA)
        // Поле / Переменная
        add("field",        UPDefault,  NCField)
        add("def",          UPDefault,  NCDef)
        // Преобразование, Проверка, Получение типов
        add("as",           UPDefault,  NCAs)
        add("is",           UPDefault,  NCIs)
        add("typeof",       UPDefault,  NCTypeof)
        // Пустой блок
        add("unit",         UPDefault,  NCUnit)
    }

    override fun inject(parser: Parser, ctx: ParsingContext) {
        if (!ctx.loadedModules.contains(this)) {
            super.inject(parser, ctx)
            StdValue.inject(parser, ctx)
        }
    }

    override fun inject(unparser: Unparser, ctx: UnparsingContext) {
        if (!ctx.loadedModules.contains(this)) {
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