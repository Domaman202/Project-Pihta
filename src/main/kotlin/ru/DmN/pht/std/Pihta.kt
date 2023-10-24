package ru.DmN.pht.std

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.std.compiler.java.PihtaJava
import ru.DmN.pht.std.parser.macros
import ru.DmN.pht.std.parsers.*
import ru.DmN.pht.std.processor.NRCCall
import ru.DmN.pht.std.processor.ctx.GlobalContext
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.processors.*
import ru.DmN.pht.std.unparsers.NUNs
import ru.DmN.pht.std.ups.*
import ru.DmN.pht.std.utils.StdModule
import java.util.*
import kotlin.collections.HashMap

object Pihta : StdModule("pht") {
    init {
        /// APP

        add("app",      NUPDefault, NRApp)
        add("app-fn",   NUPDefault, NRAppFn)

        /// BASE

        // Пространства имён
        add("ns",       NPDefault, NUNs, NRNewNs)
        add("sns",      NPDefault, NUNs, NRSubNs)
        // Пустой блок
        add("unit",     NUPDefault)

        /// COLLECTIONS

        // Перебор коллекций
        add("for",          NUPDefault, NRFor)
        // Создание коллекций
        add("list-of",      NUPDefault, NRListOf)
        // Работа с массивом
        add("array-size",   NUPDefault, NRArraySize)
        add("aset",         NUPDefault, NRASet)
        add("!aset",        NUPASet)
        add("aget",         NUPDefault, NRAGet)
        add("!aget",        NUPAGet)
        // Создание массива
        add("new-array",    NUPDefault, NRNewArray)
        add("!new-array",   NUPNewArrayX)
        add("array-of",     NUPDefault, NRArrayOf)

        /// ENUMS

        add("enum",     NUPEnum)
        add("ector",    NUPCtor)
        add("efield",   NUPEField)

        /// FP

        // Функция
        add("defn",     NUPDefn)
        // Лямбда
        add("rfn",      NUPDefault, NRRFn)
        add("fn",       NUPDefault, NRFn)
        // Тело
        add("body",     NUPDefault, NRBody)
        // Цикл
        add("cycle",    NUPDefault, NRCycle)
        add("!cycle",   NUPDefaultX)
        // Условие
        add("if",       NUPDefault, NRIf)
        // Переменные
        add("def",      NUPDefault, NRDef)
        add("!def",     NUPDef)
        // Работа с типами
        add("as",       NUPDefault, NRAs)
        add("!as",      NUPAs)
        add("is",       NUPDefault, NRIs)
        add("typeof",   NUPDefault, NRTypeof)
        // Возвраты
        add("ret",      NUPDefault, NRRet)
        add("yield",    NUPDefault, NRYield)
        // Сеттеры
        add("set",      NUPSetA)
        add("set!",     NUPSetB)
        // Геттеры
        add("get",      NUPGetA)
        add("get!",     NUPGetB)

        /// IMPORTS

        add("import",       NUPImport)
        add("alias-type",   NUPAliasType)

        /// MACRO

        add("defmacro",     NUPDefMacro)
        add("macro-unroll", NUPMacroUnroll)
        add("macro-inline", NUPMacroInline)
        add("macro-arg",    NUPMacroArg)
        add("macro!",       NUPMacro)

        /// MATH

        for (name in listOf("=", "!=", "<", "<=", ">=", ">")) {
            add(name,           NUPCompare, NRCompare)
            add("!$name", NUPCompare)
        }
        for (name in listOf("+", "-", "*", "/", "%")) {
            add(name,           NUPMath,    NRMath)
            add("!$name", NUPMath)
        }
        for (name in listOf("++", "--")) {
            add(name,           NUPIncDec,  NRIncDec)
            add("!$name", NUPIncDec)
        }
        add("!",    NUPDefault, NRNot)
        add("!!",   NUPDefaultX)

        /// OOP

        // Аннотации
        add("@abstract",NUPDefault, NRAbstract)
        add("@final",   NUPDefault, NRFinal)
        add("@open",    NUPDefault, NROpen)
        add("@static",  NUPDefault, NRStatic)
        add("@varargs", NUPDefault, NRVarargs)

        // Объект / Класс / Интерфейс
        add("obj",  NUPClass)
        add("cls",  NUPClass)
        add("inf",  NUPClass)
        // Расширение
        add("efn",  NUPEFn)
        // Конструктор
        add("ctor", NUPCtor)
        // Поле
        add("field",    NUPField)
        // Создание нового объекта
        add("new",      NUPNew)
        // Вызов конструктора / метода
        add("ccall",    NUPDefault, NRCCall)
        add("mcall",    NUPMCallA)
        add("mcall!",   NUPMCallB)
        // Сеттеры
        add("fset",     NUPFSetA)
        add("fset!",    NUPFSetB)
        // Геттеры
        add("fget",     NUPDefault, NRFGetA)
        add("!fget",    NUPFGetA)
        add("fget!",    NUPFGetB)

        /// OUT

        add("print",    NUPDefault, NRPrint)
        add("println",  NUPDefault, NRPrint)

        /// UTILS

        // Прочее
        add("skip",         NPSkip)
        add("valn-repeat",  NUPDefault, NRValnRepeat)
        // Символы
        add("rand-symbol",  NUPDefault, NRRandSymbol)
        add("lazy-symbol",  NUPLazySymbol)
        add("symbol",       NUPDefault, NRSymbol)
        // Compile-Type Константы
        add("*module-name*",NUPDefault, NRCTSC { _, ctx -> ctx.module.name })
        add("*type-name*",  NUPDefault, NRCTSC { _, ctx -> ctx.clazz.name })
        add("*fn-name*",    NUPDefault, NRCTSC { _, ctx -> ctx.method.name })
        add("*ns-name*",    NUPDefault, NRCTSC { _, ctx -> ctx.global.namespace })

        /// VALUE

        // Имя или get
        add("get-or-name!", NUPGetOrName)
        // Список выражений
        add("valn",         NUPDefault, NPValnA)
        add("valn!",        NUPValnB)
        // Тип массива
        add("array-type",   NUPDefault, NRArrayType)
        // Значение
        add("value",        NUPValue)
        add("value!",       NPValueB, null, null)

        /// DEBUG

        add("debug",        NUPDebug)

        ///

        PihtaJava.init()
    }

    override fun inject(parser: Parser, ctx: ParsingContext) {
        if (!ctx.loadedModules.contains(this))
            ctx.macros = Stack()
        super.inject(parser, ctx)
    }

    override fun inject(processor: Processor, ctx: ProcessingContext, mode: ValType): List<Node>? {
        if (!ctx.loadedModules.contains(this)) {
            processor.contexts.macros = HashMap()
            ctx.global = GlobalContext()
        }
        return super.inject(processor, ctx, mode)
    }
}

// todo: fn with variables
// todo: lexer stream
// todo: no return instruction return
// todo: прикол есть такой, называется compute, можно использовать вместо process, чтобы не обосраться в работе с нодами (isConst и т.д.) в calc моментах, в process тож можно, но лучшее в process это process