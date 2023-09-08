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
import ru.DmN.pht.std.base.unparsers.NUDefault
import ru.DmN.pht.std.base.unparsers.NUFieldSet
import ru.DmN.pht.std.base.unparsers.NUGetOrName
import ru.DmN.pht.std.base.unparsers.NUSet
import ru.DmN.pht.std.value.StdValue

object StdBase : Module("std/base") {
    init {
        // Импорт
        add("import-extends",   NPDefault,  NUDefault,  NRDefault,  NCImportExtends)
        add("import",           NPDefault,  NUDefault,  NRDefault,  NCImport)
        // Пространство Имён
        add("ns",       NPDefault,  NUDefault,  NRDefault,  NCNewNs)
        add("sub-ns",   NPDefault,  NUDefault,  NRDefault,  NCSubNs)
        // Аннотации
        add("@abstract",NPDefault,  NUDefault,  NRDefault,  NCAnnotation)
        add("@bridge",  NPDefault,  NUDefault,  NRDefault,  NCAnnotation)
        add("@final",   NPDefault,  NUDefault,  NRDefault,  NCAnnotation)
        add("@generic", NPDefault,  NUDefault,  NRDefault,  NCGeneric)
        add("@override",NPDefault,  NUDefault,  NRDefault,  NCAnnotation)
        add("@static",  NPDefault,  NUDefault,  NRDefault,  NCAnnotation)
        add("@varargs", NPDefault,  NUDefault,  NRDefault,  NCAnnotation)
        // Объект / Класс / Интерфейс
        add("obj",  NPDefault,  NUDefault,  NRDefault,  NCClass)
        add("cls",  NPDefault,  NUDefault,  NRDefault,  NCClass)
        add("itf",  NPDefault,  NUDefault,  NRDefault,  NCClass)
        // Расширение / Функция
        add("efn",  NPDefault,  NUDefault,  NRDefault,  NCExFn)
        add("bfn",  NPDefault,  NUDefault,  NRDefault,  NCBridgeFn)
        add("defn", NPDefault,  NUDefault,  NRDefault,  NCDefn)
        add("rfn",  NPDefault,  NUDefault,  NRDefault,  NCRfn)
        add("fn",   NPDefault,  NUDefault,  NRDefault,  NCFn)
        // Циклы
        add("repeat",   NPDefault,  NUDefault,  NRDefault,  NCRepeat)
        add("while",    NPDefault,  NUDefault,  NRDefault,  NCWhile)
        // Условия
        add("if",       NPDefault,  NUDefault,  NRDefault,  NCIf)
        // Выход
        add("return",   NPDefault,  NUDefault,  NRDefault,  NCReturn)
        add("yield",    NPDefault,  NUDefault,  NRDefault,  NCYield)
        // Тело
        add("body",     NPDefault,  NUDefault,  NRDefault,  NCBody)
        // Создать / Выполнить / Вызвать Супер-Конструктор / Вызвать
        add("new",      NPDefault,  NUDefault,  NRDefault,  NCNew)
        add("ccall",    NPDefault,  NUDefault,  NRDefault,  NCCtorCall)
        add("mcall!",   NPMethodCallB)
        add("mcall",    NPDefault,  NUDefault,  NRDefault,  NCMethodCallA)
        // Сеттеры
        add("fset!",    NPFieldSet, NUFieldSet, NRDefault,  NCSetB)
        add("set!",     NPSetB,     NUSet,      NRDefault,  NCSetB)
        add("set",      NPSetA,     NUDefault,  NRDefault,  NCSetA)
        // Геттеры
        add("fget!",        processor = NRDefault,  compiler =  NCFieldGetB)
        add("fget",         NPDefault,  NUDefault,  NRDefault,  NCFieldGetA)
        add("get!",         NPGet,      NUGetOrName,NRDefault,  NCGetB)
        add("get",          NPDefault,  NUDefault,  NRDefault,  NCGetA)
        // Поле / Переменная
        add("field",        NPDefault,  NUDefault,  NRDefault,  NCField)
        add("def",          NPDefault,  NUDefault,  NRDefault,  NCDef)
        // Преобразование, Проверка, Получение типов
        add("as",           NPDefault,  NUDefault,  NRDefault,  NCAs)
        add("is",           NPDefault,  NUDefault,  NRDefault,  NCIs)
        add("typeof",       NPDefault,  NUDefault,  NRDefault,  NCTypeof)
        // Пустой блок
        add("unit",         NPDefault,  NUDefault,  NRDefault,  NCUnit)
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