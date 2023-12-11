package ru.DmN.pht.std

import ru.DmN.pht.std.ast.*
import ru.DmN.pht.std.compiler.java.PihtaJava
import ru.DmN.pht.std.parser.utils.clearMacros
import ru.DmN.pht.std.parser.utils.macros
import ru.DmN.pht.std.parser.utils.phtParseNode
import ru.DmN.pht.std.parsers.NPSkip
import ru.DmN.pht.std.parsers.NPValnA
import ru.DmN.pht.std.processor.ctx.GlobalContext
import ru.DmN.pht.std.processor.utils.*
import ru.DmN.pht.std.processors.*
import ru.DmN.pht.std.ups.*
import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parser.utils.parsersPool
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.ups.NUPDefault
import ru.DmN.siberia.utils.Module
import ru.DmN.siberia.utils.adda
import ru.DmN.siberia.utils.addb
import java.util.*

object Pihta : Module("pht") {
    init {
        // a
        adda("add",          NUPMath,    NRMath)
        adda("!add",         NUPMath)
        adda("aget",         NUPDefault, NRAGet)
        adda("alias-type",   NUPDefault, NRAliasType)
        adda("!alias-type",  NUPAliasType)
        adda("and",          NUPMath,    NRMath)
        adda("!and",         NUPMath)
        adda("!aget",        NUPAGet)
        adda("app",          NUPDefault, NRApp)
        adda("app-fn",       NUPDefault, NRAppFn)
        adda("array-of",     NUPDefault, NRArrayOf)
        adda("array-of-type",NUPDefault, NRArrayOfType)
        adda("array-size",   NUPDefault, NRArraySize)
        adda("array-type",   NUPDefault, NRArrayType)
        adda("as",           NUPGensDefault, NRAs)
        adda("!as",          NUPAs)
        adda("as-gens",      NUPDefault, NRAsGens)
        adda("aset",         NUPDefault, NRASet)
        adda("!aset",        NUPASet)
        // b
        adda("body",         NUPDefault, NRBody)
        adda("break",        NUPDefault, NRNamedList)
        adda("!break",       NUPNamedList)
        // c
        adda("catch",        NUPDefault, NRCatch)
        adda("!catch",       NUPCatch)
        adda("ccall",        NUPDefault, NRCCall)
        adda("cls",          NUPDefault, NRClass)
        adda("!cls",         NUPClass)
        adda("comment",      NUPComment)
        adda("continue",     NUPDefault, NRNamedList)
        adda("!continue",    NUPNamedList)
        adda("ctor",         NUPCtor)
        adda("cycle",        NUPDefault, NRCycle)
        adda("!cycle",       NUPDefaultX)
        // d
        adda("debug",        NUPDebug)
        adda("dec",          NUPIncDecA,  NRIncDec)
        adda("dec-",         NUPIncDecA,  NRIncDec)
        adda("!dec",         NUPIncDecB)
        adda("def",          NUPDefault, NRDef)
        adda("!def",         NUPDef)
        adda("def-set",      NUPDefault, NRDefSet)
        adda("defmacro",     NUPDefMacro)
        adda("defn",         NUPDefault, NRDefn)
        adda("!defn",        NUPDefn)
        adda("div",          NUPMath,    NRMath)
        adda("!div",         NUPMath)
        // e
        adda("ector",        NUPCtor)
        adda("efld",         NUPEField)
        adda("efn",          NUPEFn)
        adda("enum",         NUPEnum)
        adda("eq",           NUPCompare, NRCompare)
        adda("!eq",          NUPCompare)
        // f
        adda("fget",         NUPDefault, NRFGetA)
        adda("fget!",        NUPFGetB)
        adda("!fget",        NUPFGetA)
        adda("fld",          NUPFieldA)
        adda("!fld",         NUPFieldB)
        adda("fn",           NUPDefault, NRFn)
        adda("!fn",          NUPFn)
        adda("for",          NUPDefault, NRFor)
        adda("fset",         NUPFSetA)
        adda("fset!",        NUPFSetB)
        // g
        adda("get",          NUPGetA)
        adda("get!",         NUPGetB)
        adda("get-or-name!", NUPGetOrName)
        adda("great",        NUPCompare, NRCompare)
        adda("!great",       NUPCompare)
        adda("great-or-eq",  NUPCompare, NRCompare)
        adda("!great-or-eq", NUPCompare)
        // i
        adda("if",           NUPDefault, NRIf)
        adda("import",       NUPImport)
        adda("inc",          NUPIncDecA,  NRIncDec)
        adda("inc-",         NUPIncDecA,  NRIncDec)
        adda("!inc",         NUPIncDecB)
        adda("is",           NUPDefault, NRIs)
        adda("itf",          NUPDefault, NRClass)
        adda("!itf",         NUPClass)
        // l
        adda("lazy-symbol",  NUPLazySymbol)
        adda("less",         NUPCompare, NRCompare)
        adda("!less",        NUPCompare)
        adda("less-or-eq",   NUPCompare, NRCompare)
        adda("!less-or-eq",  NUPCompare)
        adda("list-of",      NUPDefault, NRListOf)
        // m
        adda("macro",        NUPMacro)
        adda("macro-arg",    NUPMacroArg)
        adda("macro-inline", NUPMacroInline)
        adda("macro-unroll", NUPMacroUnroll)
        adda("mcall",        NUPDefault, NRMCall) // todo: select super & auto new futures for '.'
        adda("mcall!",       NUPMCall)
        adda("!mcall",       NUPMCallX)
        adda("mul",          NUPMath,    NRMath)
        adda("!mul",         NUPMath)
        // n
        adda("named-block",  NUPDefault,     NRNamedList)
        adda("!named-block", NUPNamedList)
        adda("neg",          NUPMath,        NRMath)
        adda("!neg",         NUPMath)
        adda("new",          NUPGensDefault, NRNew)
        adda("!new",         NUPNew)
        adda("new-array",    NUPDefault,     NRNewArray)
        adda("!new-array",   NUPNewArrayX)
        adda("not",          NUPCompare,     NRCompare)
        adda("!not",         NUPCompare)
        adda("not-eq",       NUPCompare,     NRCompare)
        adda("!not-eq",      NUPCompare)
        adda("ns",           NUPDefault,     NRNs)
        adda("!ns",          NUPNs)
        // o
        adda("obj",          NUPDefault, NRClass)
        adda("!obj",         NUPClass)
        adda("or",           NUPMath,    NRMath)
        adda("!or",          NUPMath)
        // p
        adda("print",        NUPDefault, NRPrint)
        adda("println",      NUPDefault, NRPrint)
        adda("progn",        NUPDefault, NRPrognA)
        addb("progn-",       NUPPrognB)
        adda("progn!",       NUPPrognC)
        // r
        adda("rand-symbol",  NUPDefault, NRRandSymbol)
        adda("rem",          NUPMath,    NRMath)
        adda("!rem",         NUPMath)
        adda("ret",          NUPDefault, NRRet)
        adda("!ret",         NUPRet)
        // s
        adda("set",          NUPSetA)
        adda("set!",         NUPSetB)
        adda("shift-left",   NUPMath,    NRMath)
        adda("!shift-left",  NUPMath)
        adda("shift-right",  NUPMath,    NRMath)
        adda("!shift-right", NUPMath)
        adda("skip",         NUPDefault, NPSkip)
        adda("sub",          NUPMath,    NRMath)
        adda("!sub",         NUPMath)
        adda("symbol",       NUPDefault, NRSymbol)
        // t
        adda("test-fn",      NUPDefault, NRTestFn)
        adda("throw",        NUPDefault)
        adda("typeof",       NUPDefault, NRTypeof)
        // u
        adda("unit",         NUPUnit)
        // v
        adda("valn",         NUPStdDefault, NPValnA)
        adda("valn!",        NUPValnB)
        adda("valn-repeat",  NUPStdDefault, NRValnRepeat)
        adda("value",        NUPValueA)
        adda("value!",       NUPValueB)
        // w
        adda("with-gens",    NUPDefault,    NRWithGens)
        // x
        adda("xor",          NUPMath,       NRMath)
        adda("!xor",         NUPMath)
        // y
        adda("yield",        NUPDefault,    NRYield)

        // Аннотации
        adda("@abstract",NUPDefault, NRSA { it, _, _ -> if (it is IAbstractlyNode)   it.abstract = true })
        adda("@final",   NUPDefault, NRSA { it, _, _ -> if (it is IFinallyNode)      it.final = true })
        adda("@open",    NUPDefault, NRSA { it, _, _ -> if (it is IFinallyNode)      it.final = false })
        adda("@static",  NUPDefault, NRSA { it, _, _ -> if (it is IStaticallyNode)   it.static = true })
        adda("@varargs", NUPDefault, NRSA { it, _, _ -> if (it is IVarargNode)       it.varargs = true })

        // Compile-Type Константы
        adda("*module-name*",NUPDefault, NRCTSC { _, ctx -> ctx.module.name })
        adda("*type-name*",  NUPDefault, NRCTSC { _, ctx -> ctx.clazz.name })
        adda("*fn-name*",    NUPDefault, NRCTSC { _, ctx -> ctx.method.name })
        adda("*ns-name*",    NUPDefault, NRCTSC { _, ctx -> ctx.global.namespace })

        // Развёртки
        adda("->", NUPDefault, NRUnrollA)
        adda("<-", NUPDefault, NRUnrollB)

        // Мат/Лог операции
        "++" to "inc"
        "--" to "dec"
        "+"  to "add"
        "-"  to "sub"
        "*"  to "mul"
        "/"  to "div"
        "%"  to "rem"
        "!"  to "not"
        "="  to "eq"
        "!=" to "not-eq"
        ">"  to "great"
        ">=" to "great-or-eq"
        "<"  to "less"
        "<=" to "less-or-eq"
        ">>" to "shift-right"
        "<<" to "shift-left"

        ///

        PihtaJava.init()
    }

    infix fun String.to(alias: String) {
        add(this, NUPNodeAlias(alias))
    }

    override fun load(parser: Parser, ctx: ParsingContext) {
        if (!ctx.loadedModules.contains(this)) {
            ctx.macros = Stack()
            ctx.parsersPool.push(parser.parseNode)
            parser.parseNode = { phtParseNode(it) }
        }
        super.load(parser, ctx)
    }

    override fun clear(parser: Parser, ctx: ParsingContext) {
        if (ctx.loadedModules.contains(this)) {
            parser.parseNode = ctx.parsersPool.pop()
        }
    }

    override fun unload(parser: Parser, ctx: ParsingContext) {
        if (ctx.loadedModules.contains(this)) {
            ctx.clearMacros()
            parser.parseNode = ctx.parsersPool.pop()
        }
        super.unload(parser, ctx)
    }

    override fun load(processor: Processor, ctx: ProcessingContext, mode: ValType): Boolean {
        if (!ctx.loadedModules.contains(this)) {
            processor.contexts.macros = HashMap()
            ctx.global = GlobalContext()
        }
        return super.load(processor, ctx, mode)
    }
}