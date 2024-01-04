package ru.DmN.pht.std

import ru.DmN.pht.processors.NRPrognB
import ru.DmN.pht.processors.NRTypedGet
import ru.DmN.pht.processors.NRTypedGetB
import ru.DmN.pht.std.ast.IAbstractlyNode
import ru.DmN.pht.std.ast.IFinallyNode
import ru.DmN.pht.std.ast.IStaticallyNode
import ru.DmN.pht.std.ast.IVarargNode
import ru.DmN.pht.std.compiler.java.PihtaJava
import ru.DmN.pht.std.node.NodeParsedTypes.*
import ru.DmN.pht.std.node.NodeTypes.*
import ru.DmN.pht.std.parser.utils.clearMacros
import ru.DmN.pht.std.parser.utils.macros
import ru.DmN.pht.std.parser.utils.phtParseNode
import ru.DmN.pht.std.parsers.*
import ru.DmN.pht.std.processor.ctx.GlobalContext
import ru.DmN.pht.std.processor.utils.clazz
import ru.DmN.pht.std.processor.utils.global
import ru.DmN.pht.std.processor.utils.macros
import ru.DmN.pht.std.processor.utils.method
import ru.DmN.pht.std.processors.*
import ru.DmN.pht.unparsers.*
import ru.DmN.siberia.Parser
import ru.DmN.siberia.Processor
import ru.DmN.siberia.node.INodeType
import ru.DmN.siberia.node.NodeTypes.PROGN
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.parser.utils.parsersPool
import ru.DmN.siberia.parsers.INodeParser
import ru.DmN.siberia.parsers.SimpleNP
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.ValType
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.processors.NRProgn
import ru.DmN.siberia.unparsers.NUDefault
import ru.DmN.siberia.utils.Module
import java.util.*
import ru.DmN.pht.std.processors.NRProgn as NRPrognA

object Pihta : Module("pht") {
    override fun initParsers() {
        // a
        addSNP(ADD)
        addSNP(AGET)
        addSNP(ALIAS_TYPE)
        addSNP(AND)
        addSNP(APP)
        addSNP(APP_FN)
        addSNP(ARRAY_OF)
        addSNP(ARRAY_OF_TYPE)
        addSNP(ARRAY_SIZE)
        addSNP(ARRAY_TYPE)
        addSNP(AS)
        addSNP(AS_GENS)
        addSNP(ASET)
        // b
        addSNP(BODY)
        addSNP(BREAK)
        // c
        addSNP(CATCH)
        addSNP(CCALL)
        addSNP(CLS)
        addNP("comment",  NPComment)
        addSNP(COND)
        addSNP(CONTINUE)
        addSNP(CTOR)
        addSNP(CYCLE)
        // d
        addNP("debug",    NPDebug)
        addSNP(DEC_PRE)
        addSNP(DEC_POST)
        addSNP(DEF)
        addSNP(DEF_SET)
        addNP("defmacro", NPDefMacro)
        addSNP(DEFN)
        addSNP(DIV)
        // e
        addSNP(ECTOR)
        addSNP(EFLD)
        addSNP(EFN)
        addSNP(ENUM)
        addSNP(EQ)
        // f
        addSNP(FGET_A)
        addNP("fld",          NPFld)
        addSNP(FN)
        addSNP(FOR)
        addSNP(FSET_A)
        // g
        addNP("get",          NPGet)
        addSNP(GET_B)
        addNP("get-or-name!", NPGetOrName)
        addSNP(GREAT)
        addSNP(GREAT_OR_EQ)
        // i
        addSNP(IF)
        addNP("import",       NPImport)
        addSNP(INC_PRE)
        addSNP(INC_POST)
        addSNP(IS)
        addSNP(ITF)
        // l
        addNP("lazy-symbol",  NPLazySymbol)
        addSNP(LESS)
        addSNP(LESS_OR_EQ)
        addSNP(LIST_OF)
        // m
        addNP("macro",        NPMacro)
        addNP("macro-arg",    NPMacroArg)
        addNP("macro-inline", NPMacroInline)
        addNP("macro-unroll", NPMacroUnroll)
        addSNP(MCALL)
        addNP("mcall!",       NPMCallB)
        addSNP(MUL)
        // n
        addSNP(NAMED_BLOCK)
        addSNP(NEG)
        addSNP(NEW)
        addSNP(NEW_ARRAY)
        addSNP(NOT)
        addSNP(NOT_EQ)
        addSNP(NS)
        // o
        addSNP(OBJ)
        addSNP(OR)
        // p
        addSNP(PRINT)
        addSNP(PRINTLN)
        addNP("progn-", NPPrognB)
        // r
        addSNP(RAND_SYMBOL)
        addSNP(RANGE)
        addSNP(REM)
        addSNP(RET)
        addSNP(ROLL_LEFT)
        addSNP(ROLL_RIGHT)
        // s
        addNP("set",    NPSetA)
        addNP("set!",   NPSetB)
        addSNP(SHIFT_LEFT)
        addSNP(SHIFT_RIGHT)
        addNP("skip",   NPSkip)
        addSNP(SUB)
        addSNP(SYMBOL)
        addSNP(SYMBOL_CLS)
        // t
        addSNP(TEST_FN)
        addSNP(THROW)
        addSNP(TYPED_GET)
        addSNP(TYPEOF)
        // u
        addNP("unit",   NPUnit)
        // v
        addSNP(VALN)
        addNP("valn!",  NPValnB)
        addSNP(VALN_REPEAT)
        addNP("value",  NPValueA)
        addNP("value!", NPValueB)
        // w
        addSNP(WITH_GENS)
        // x
        addSNP(XOR)
        // y
        addSNP(YIELD)

        // @
        addSNP(ANN_ABSTRACT)
        addSNP(ANN_FINAL)
        addSNP(ANN_OPEN)
        addSNP(ANN_STATIC)
        addSNP(ANN_VARARGS)

        // *
        addSNP(CTC_MODULE_NAME)
        addSNP(CTC_TYPE_NAME)
        addSNP(CTC_FN_NAME)
        addSNP(CTC_NS_NAME)

        // Символьные аналоги

        // a
        "+"  to "add"
        // d
        "--" to "dec"
        "/"  to "div"
        // e
        "="  to "eq"
        // g
        ">"  to "great"
        ">=" to "great-or-eq"
        // i
        "++" to "inc"
        // l
        "<"  to "less"
        "<=" to "less-or-eq"
        // m
        "*"  to "mul"
        // n
        "!"  to "not"
        "!=" to "not-eq"
        // r
        "%"  to "rem"
        "->" to "roll-left"
        "<-" to "roll-right"
        // s
        "<<" to "shift-left"
        ">>" to "shift-right"
        "-"  to "sub"
    }

    private fun addNP(pattern: String, parser: INodeParser) {
        add(pattern.toRegularExpr(), parser)
    }

    private fun addSNP(type: INodeType) {
        add(type.operation.toRegularExpr(), SimpleNP(type))
    }

    infix fun String.to(alias: String) {
        add(this.toRegularExpr(), NPNodeAlias(alias))
    }

    override fun initUnparsers() {
        // a
        addSNU(ADD)
        addSNU(ADD_)
        addSNU(AGET)
        addSNU(ALIAS_TYPE)
        // ALIAS_TYPE_
        addSNU(AND)
        addSNU(AND_)
        addSNU(APP)
        addSNU(APP_FN)
        addSNU(ARRAY_OF)
        addSNU(ARRAY_OF_TYPE)
        addSNU(ARRAY_SIZE)
        addSNU(ARRAY_SIZE_)
        addSNU(AS)
        add(AS_, NUAs)
        addSNU(AS_GENS)
        addSNU(ASET)
        addSNU(ASET_)
        // b
        addSNU(BODY)
        addSNU(BODY_)
        addSNU(BREAK)
        add(BREAK_,         NUNamedBlock)
        // c
        addSNU(CATCH)
        add(CATCH_,         NUCatch)
        addSNU(CCALL)
        addSNU(CLS)
        add(CLS_,           NUClass)
        addSNU(COND)
        addSNU(CONTINUE)
        add(CONTINUE_,      NUNamedBlock)
        addSNU(CTOR)
        add(CTOR_,          NUCtor)
        addSNU(CYCLE)
        addSNU(CYCLE_)
        // d
        add(DEBUG,          NUDebug)
        addSNU(DEC_PRE)
        add(DEC_PRE_,       NUIncDec)
        addSNU(DEC_POST)
        add(DEC_POST_,      NUIncDec)
        addSNU(DEF)
        add(DEF_,           NUDef)
        addSNU(DEF_SET)
        // DEFMACRO
        addSNU(DEFN)
        add(DEFN_,          NUDefn)
        addSNU(DIV)
        addSNU(DIV_)
        // e
        addSNU(ECTOR)
        addSNU(EFLD)
        // EFLD_
        addSNU(EFN)
        add(EFN_,           NUEFn)
        addSNU(ENUM)
        add(ENUM_,          NUClass)
        addSNU(EQ)
        addSNU(EQ_)
        // f
        addSNU(FGET_A)
        add(FGET_B,         NUFGetA)
        add(FGET_,          NUFGetB)
        addSNU(FLD)
        add(FLD_,           NUFld)
        addSNU(FN)
        // FN_
        addSNU(FOR)
        addSNU(FSET_A)
        add(FSET_B,         NUFSetA)
        add(FSET_,          NUFSetB)
        // g
        // GET_A
        addSNU(GET_B)
        // GET_
        add(GET_OR_NAME,    NUGetOrName)
        addSNU(GREAT)
        addSNU(GREAT_)
        addSNU(GREAT_OR_EQ)
        addSNU(GREAT_OR_EQ_)
        // i
        addSNU(IF)
        addSNU(IF_)
        add(IMPORT,         NUImport)
        add(IMPORT_,        NUImport)
        addSNU(INC_PRE)
        add(INC_PRE_,       NUIncDec)
        addSNU(INC_POST)
        add(INC_POST_,      NUIncDec)
        addSNU(IS)
        addSNU(IS_)
        addSNU(ITF)
        add(ITF_,           NUClass)
        // l
        // LAZY_SYMBOL
        addSNU(LESS)
        addSNU(LESS_)
        addSNU(LESS_OR_EQ)
        addSNU(LESS_OR_EQ_)
        addSNU(LIST_OF)
        // m
        // MACRO
        // MACRO_ARG
        // MACRO_INLINE
        // MACRO_UNROLL
        addSNU(MCALL)
        add(MCALL_,         NUMCall)
        addSNU(MUL)
        addSNU(MUL_)
        // n
        addSNU(NAMED_BLOCK)
        add(NAMED_BLOCK_,   NUNamedBlock)
        addSNU(NEG)
        addSNU(NEG_)
        addSNU(NEW)
        add(NEW_,           NUNew)
        addSNU(NEW_ARRAY)
        add(NEW_ARRAY_,     NUNewArray)
        addSNU(NOT)
        addSNU(NOT_)
        addSNU(NOT_EQ)
        addSNU(NOT_EQ_)
        addSNU(NS)
        // NS_
        // o
        addSNU(OBJ)
        // OBJ_
        addSNU(OR)
        addSNU(OR_)
        // p
        addSNU(PRINT)
        addSNU(PRINTLN)
        // PROGN_B
        // PROGN_B_
        // r
        addSNU(RAND_SYMBOL)
        addSNU(RANGE)
        addSNU(REM)
        addSNU(REM_)
        addSNU(RET)
        addSNU(ROLL_LEFT)
        addSNU(ROLL_RIGHT)
        // s
        addSNU(SET_A)
        add(SET_B,          NUSetB)
        add(SET_,           NUSetB)
        // SET_
        addSNU(SHIFT_LEFT)
        addSNU(SHIFT_LEFT_)
        addSNU(SHIFT_RIGHT)
        addSNU(SHIFT_RIGHT_)
        addSNU(SUB)
        addSNU(SUB_)
        addSNU(SYMBOL)
        addSNU(SYMBOL_CLS)
        // t
        addSNU(TEST_FN)
        addSNU(THROW)
        addSNU(THROW_)
        addSNU(TYPED_GET)
        add(TYPED_GET,      NUTypedGet)
        addSNU(TYPEOF)
        // u
        add(UNIT,           NUUnit)
        // v
        add(VALN,           NUValn)
        add(VALN_,          NUValn)
        addSNU(VALN_REPEAT)
        add(VALUE,          NUValue)
        // w
        addSNU(WITH_GENS)
        addSNU(XOR)
        addSNU(YIELD)

        // @
        addSNU(ANN_ABSTRACT)
        addSNU(ANN_ABSTRACT_)
        addSNU(ANN_FINAL)
        addSNU(ANN_FINAL_)
        addSNU(ANN_OPEN)
        addSNU(ANN_OPEN_)
        addSNU(ANN_STATIC)
        addSNU(ANN_STATIC_)
        addSNU(ANN_VARARGS)
        addSNU(ANN_VARARGS_)

        // *
        addSNU(CTC_MODULE_NAME)
        addSNU(CTC_TYPE_NAME)
        addSNU(CTC_FN_NAME)
        addSNU(CTC_NS_NAME)
    }

    private fun addSNU(type: INodeType) {
        add(type, NUDefault)
    }

    override fun initProcessors() {
        // a
        add(ADD,           NRMath)
        add(ADD_,          NRMathB)
        add(ALIAS_TYPE,    NRAliasType)
        add(AGET,          NRAGet)
        add(AGET_,         NRAGetB)
        add(AND,           NRMath)
        add(AND_,          NRMathB)
        add(APP,           NRApp)
        add(APP_FN,        NRAppFn)
        add(ARRAY_OF,      NRArrayOf)
        add(ARRAY_OF_TYPE, NRArrayOfType)
        add(ARRAY_SIZE,    NRArraySize)
        add(ARRAY_SIZE_,   NRArraySize)
        add(ARRAY_TYPE,    NRArrayType)
        add(AS,            NRAs)
        add(AS_,           NRAsB)
        add(AS_GENS,       NRAsGens)
        add(ASET,          NRASet)
        add(ASET_,         NRASet)
        // b
        add(BODY,          NRBody)
        add(BODY_,         NRBody)
        add(BREAK,         NRBreakContinue)
        //
        add(CATCH,         NRCatch)
        add(CATCH_,        NRCatch)
        add(CCALL,         NRCCall)
        add(CLS,           NRClass)
        add(COND,          NRCond)
        add(CONTINUE,      NRBreakContinue)
        add(CTOR,          NRCtor)
        add(CYCLE,         NRCycle)
        // d
        add(DEBUG,         NRDebug)
        add(DEC_PRE,       NRIncDec)
        add(DEC_PRE_,      NRIncDecB)
        add(DEC_POST,      NRIncDec)
        add(DEC_POST_,     NRIncDecB)
        add(DEF,           NRDef)
        add(DEF_SET,       NRDefSet)
        add(DEFMACRO,      NRDefMacro)
        add(DEFN,          NRDefn)
        add(DIV,           NRMath)
        add(DIV_,          NRMathB)
        // e
        add(EFLD,          NREFld)
        add(EFN,           NREFn)
        add(ENUM,          NREnum)
        add(EQ,            NRCompare)
        add(EQ_,           NRCompareB)
        // f
        add(FGET_A,        NRFGetA)
        add(FGET_B,        NRFGetB)
        add(FGET_,         NRFGet)
        add(FLD,           NRFld)
        add(FN,            NRFn)
        add(FN_,           NRFnB)
        add(FOR,           NRFor)
        add(FSET_A,        NRFSetA)
        add(FSET_B,        NRFSetB)
        // g
        add(GET_B,         NRGetB)
        add(GET_OR_NAME,   NRGetOrName)
        add(GREAT,         NRCompare)
        add(GREAT_,        NRCompareB)
        add(GREAT_OR_EQ,   NRCompare)
        add(GREAT_OR_EQ_,  NRCompareB)
        // i
        add(IF,            NRIf)
        add(IF_,           NRIfB)
        add(IMPORT,        NRImport)
        add(INC_PRE,       NRIncDec)
        add(INC_PRE_,      NRIncDecB)
        add(INC_POST,      NRIncDec)
        add(INC_POST_,     NRIncDecB)
        add(IS,            NRIs)
        add(ITF,           NRClass)
        // l
        add(LAZY_SYMBOL,   NRLazySymbol)
        add(LESS,          NRCompare)
        add(LESS_,         NRCompareB)
        add(LESS_OR_EQ,    NRCompare)
        add(LESS_OR_EQ_,   NRCompareB)
        add(LIST_OF,       NRListOf)
        // m
        add(MACRO,         NRMacro)
        add(MACRO_ARG,     NRMacroArg)
        add(MACRO_INLINE,  NRMacroInline)
        add(MACRO_UNROLL,  NRMacroUnroll)
        add(MCALL,         NRMCall)
        add(MCALL_,        NRMCallB)
        add(MUL,           NRMath)
        add(MUL_,          NRMathB)
        // n
        add(NAMED_BLOCK,   NRNamedList)
        add(NAMED_BLOCK_,  NRProgn)
        add(NEG,           NRMath)
        add(NEG_,          NRMathB)
        add(NEW,           NRNew)
        add(NEW_,          NRNewB)
        add(NEW_ARRAY,     NRNewArray)
        add(NEW_ARRAY_,    NRNewArrayB)
        add(NOT,           NRCompare)
        add(NOT_,          NRCompareB)
        add(NOT_EQ,        NRCompare)
        add(NOT_EQ_,       NRCompareB)
        add(NS,            NRNs)
        add(NS_,           NRNs)
        // o
        add(OBJ,           NRClass)
        add(OBJ_,          NRClass)
        add(OR,            NRMath)
        add(OR_,           NRMathB)
        // p
        add(PRINT,         NRPrint)
        add(PRINTLN,       NRPrint)
        add(PROGN,         NRPrognA)
        add(PROGN_B,       NRPrognB)
        // r
        add(RAND_SYMBOL,   NRRandSymbol)
        add(RANGE,         NRRange)
        add(REM,           NRMath)
        add(REM_,          NRMathB)
        add(RET,           NRRet)
        add(ROLL_LEFT,     NRRollLeft)
        add(ROLL_RIGHT,    NRRollRight)
        // s
        add(SET_B,         NRSet)
        add(SHIFT_LEFT,    NRMath)
        add(SHIFT_LEFT_,   NRMathB)
        add(SHIFT_RIGHT,   NRMath)
        add(SHIFT_RIGHT_,  NRMathB)
        add(SUB,           NRMath)
        add(SUB_,          NRMathB)
        add(SYMBOL,        NRSymbol)
        add(SYMBOL_CLS,    NRSymbolCls)
        // t
        add(TEST_FN,       NRTestFn)
        add(THROW,         NRThrow)
        add(TYPED_GET,     NRTypedGet)
        add(TYPED_GET_,    NRTypedGetB)
        add(TYPEOF,        NRTypeof)
        // u
        add(UNIT,          NRUnit)
        // v
        add(VALN,          NRValn)
        add(VALN_,         NRValn)
        add(VALN_REPEAT,   NRValnRepeat)
        add(VALUE,         NRValue)
        // w
        add(WITH_GENS,     NRWithGens)
        // x
        add(XOR,           NRMath)
        add(XOR_,          NRMathB)
        // y
        add(YIELD,         NRYield)

        // @
        add(ANN_ABSTRACT, NRSA { it, _, _ -> if (it is IAbstractlyNode)   it.abstract = true })
        add(ANN_FINAL,    NRSA { it, _, _ -> if (it is IFinallyNode)      it.final = true })
        add(ANN_OPEN,     NRSA { it, _, _ -> if (it is IFinallyNode)      it.final = false })
        add(ANN_STATIC,   NRSA { it, _, _ -> if (it is IStaticallyNode)   it.static = true })
        add(ANN_VARARGS,  NRSA { it, _, _ -> if (it is IVarargNode)       it.varargs = true })

        // *
        add(CTC_MODULE_NAME, NRCTSC { _, ctx -> ctx.module.name })
        add(CTC_TYPE_NAME,   NRCTSC { _, ctx -> ctx.clazz.name })
        add(CTC_FN_NAME,     NRCTSC { _, ctx -> ctx.method.name })
        add(CTC_NS_NAME,     NRCTSC { _, ctx -> ctx.global.namespace })
    }

    override fun initCompilers() {
        PihtaJava(this).init()
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