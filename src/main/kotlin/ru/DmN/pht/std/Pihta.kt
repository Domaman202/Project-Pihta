package ru.DmN.pht.std

import ru.DmN.pht.base.Parser
import ru.DmN.pht.base.Processor
import ru.DmN.pht.base.ast.Node
import ru.DmN.pht.base.parser.ParsingContext
import ru.DmN.pht.base.parsers.NPDefault
import ru.DmN.pht.base.processor.ProcessingContext
import ru.DmN.pht.base.processor.ValType
import ru.DmN.pht.std.ast.*
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
        // a
        add("add",          NUPMath,    NRMath)
        add("!add",         NUPMath)
        add("aget",         NUPDefault, NRAGet)
        add("alias-type",   NUPDefault, NRAliasType)
        add("!alias-type",  NUPAliasType)
        add("!aget",        NUPAGet)
        add("app",          NUPDefault, NRApp)
        add("app-fn",       NUPDefault, NRAppFn)
        add("array-of",     NUPDefault, NRArrayOf)
        add("array-size",   NUPDefault, NRArraySize)
        add("array-type",   NUPDefault, NRArrayType)
        add("as",           NUPDefault, NRAs)
        add("!as",          NUPAs)
        add("aset",         NUPDefault, NRASet)
        add("!aset",        NUPASet)
        // b
        add("body",         NUPDefault, NRBody)
        // c
        add("ccall",        NUPDefault, NRCCall)
        add("cls",          NUPDefault, NRClass)
        add("!cls",         NUPClass)
        add("ctor",         NUPCtor)
        add("cycle",        NUPDefault, NRCycle)
        add("!cycle",       NUPDefaultX)
        // d
        add("debug",        NUPDebug)
        add("dec",          NUPIncDec,  NRIncDec)
        add("!dec",         NUPIncDec)
        add("def",          NUPDefault, NRDef)
        add("!def",         NUPDef)
        add("defmacro",     NUPDefMacro)
        add("defn",         NUPDefault, NRDefn)
        add("!defn",        NUPDefn)
        add("div",          NUPMath, NRMath)
        add("!div",         NUPMath)
        // e
        add("ector",        NUPCtor)
        add("efield",       NUPEField)
        add("efn",          NUPEFn)
        add("enum",         NUPEnum)
        add("eq",           NUPCompare, NRCompare)
        add("!eq",          NUPCompare)
        // f
        add("fget",         NUPDefault, NRFGetA)
        add("fget!",        NUPFGetB)
        add("!fget",        NUPFGetA)
        add("field",        NUPDefault, NRField)
        add("!field",       NUPField)
        add("fn",           NUPDefault, NRFn)
        add("!fn",          NUPFn)
        add("for",          NUPDefault, NRFor)
        add("fset",         NUPFSetA)
        add("fset!",        NUPFSetB)
        // g
        add("get",          NUPGetA)
        add("get!",         NUPGetB)
        add("get-or-name!", NUPGetOrName)
        add("great",        NUPCompare, NRCompare)
        add("!great",       NUPCompare)
        add("great-or-eq",  NUPCompare, NRCompare)
        add("!great-or-eq", NUPCompare)
        // i
        add("if",           NUPDefault, NRIf)
        add("import",       NUPImport)
        add("inc",          NUPIncDec,  NRIncDec)
        add("!inc",         NUPIncDec)
        add("is",           NUPDefault, NRIs)
        add("itf",          NUPDefault, NRClass)
        add("!itf",         NUPClass)
        // l
        add("lazy-symbol",  NUPLazySymbol)
        add("less",         NUPCompare, NRCompare)
        add("!less",        NUPCompare)
        add("less-or-eq",   NUPCompare, NRCompare)
        add("!less-or-eq",  NUPCompare)
        add("list-of",      NUPDefault, NRListOf)
        // m
        add("macro!",       NUPMacro)
        add("macro-arg",    NUPMacroArg)
        add("macro-inline", NUPMacroInline)
        add("macro-unroll", NUPMacroUnroll)
        add("mcall",        NUPDefault, NRMCall)
        add("mcall!",       NUPMCall)
        add("!mcall",       NUPMCallX)
        add("mul",          NUPMath,    NRMath)
        add("!mul",         NUPMath)
        // n
        add("new",          NUPNew)
        add("new-array",    NUPDefault, NRNewArray)
        add("!new-array",   NUPNewArrayX)
        add("not",          NUPNot)
        add("!not",         NUPDefaultX)
        add("not-eq",       NUPCompare, NRCompare)
        add("!not-eq",      NUPCompare)
        add("ns",           NPDefault,  NUNs, NRNewNs)
        // o
        add("obj",          NUPDefault, NRClass)
        add("!obj",         NUPClass)
        // p
        add("print",        NUPDefault, NRPrint)
        add("println",      NUPDefault, NRPrint)
        // r
        add("rand-symbol",  NUPDefault, NRRandSymbol)
        add("rem",          NUPMath,    NRMath)
        add("!rem",         NUPMath)
        add("ret",          NUPDefault, NRRet)
        add("rfn",          NUPDefault, NRRFn)
        add("!rfn",         NUPRFn)
        // s
        add("set",          NUPSetA)
        add("set!",         NUPSetB)
        add("skip",         NPSkip)
        add("sns",          NPDefault,  NUNs, NRSubNs)
        add("sub",          NUPMath, NRMath)
        add("!sub",         NUPMath)
        add("symbol",       NUPDefault, NRSymbol)
        // t
        add("typeof",       NUPDefault, NRTypeof)
        // u
        add("unit",         NUPDefault)
        // v
        add("valn",         NUPDefault, NPValnA)
        add("valn!",        NUPValnB)
        add("valn-repeat",  NUPDefault, NRValnRepeat)
        add("value",        NUPValueA)
        add("value!",       NUPValueB)
        // y
        add("yield",        NUPDefault, NRYield)

        // Аннотации
        add("@abstract",NUPDefault, NRSA { it, _, _ -> if (it is IAbstractlyNode) it.abstract = true })
        add("@final",   NUPDefault, NRSA { it, _, _ -> if (it is IFinallyNode) it.final = true })
        add("@open",    NUPDefault, NRSA { it, _, _ -> if (it is IOpenlyNode) it.open = true })
        add("@static",  NUPDefault, NRSA { it, _, _ -> if (it is IStaticallyNode) it.static = true })
        add("@varargs", NUPDefault, NRSA { it, _, _ -> if (it is IVarargNode) it.varargs = true })

        // Compile-Type Константы
        add("*module-name*",NUPDefault, NRCTSC { _, ctx -> ctx.module.name })
        add("*type-name*",  NUPDefault, NRCTSC { _, ctx -> ctx.clazz.name })
        add("*fn-name*",    NUPDefault, NRCTSC { _, ctx -> ctx.method.name })
        add("*ns-name*",    NUPDefault, NRCTSC { _, ctx -> ctx.global.namespace })

        // Развёртки
        add("->", NUPDefault, NRUnrollA)
        add("<-", NUPDefault, NRUnrollB)

        // Мат/Лог операции
        add("+",    NUPMath)
        add("-",    NUPMath)
        add("*",    NUPMath)
        add("/",    NUPMath)
        add("%",    NUPMath)
        add("!",    NUPNot)
        add("=",    NUPCompare)
        add("!=",   NUPCompare)
        add(">",    NUPCompare)
        add(">=",   NUPCompare)
        add("<",    NUPCompare)
        add("<=",   NUPCompare)

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

// todo: lexer stream
// todo: no return instruction return