package ru.DmN.pht

import ru.DmN.pht.jvm.compilers.NCSkip
import ru.DmN.pht.jvm.compilers.NCUnit
import ru.DmN.pht.parser.ParserImpl
import ru.DmN.pht.parser.utils.macros
import ru.DmN.pht.parsers.*
import ru.DmN.pht.processor.NRVoid
import ru.DmN.pht.processor.ctx.*
import ru.DmN.pht.processor.utils.LinkedClassesNode
import ru.DmN.pht.processor.utils.PhtProcessingStage
import ru.DmN.pht.processors.*
import ru.DmN.pht.unparsers.*
import ru.DmN.pht.utils.*
import ru.DmN.pht.utils.Platforms.JVM
import ru.DmN.pht.utils.meta.MetadataKeys
import ru.DmN.pht.utils.node.NodeParsedTypes.*
import ru.DmN.pht.utils.node.NodeTypes.*
import ru.DmN.pht.utils.vtype.VTAuto
import ru.DmN.pht.utils.vtype.VTDynamic
import ru.DmN.siberia.compiler.Compiler
import ru.DmN.siberia.compiler.ctx.CompilationContext
import ru.DmN.siberia.compiler.utils.ModuleCompilers
import ru.DmN.siberia.compilers.NCDefault
import ru.DmN.siberia.parser.Parser
import ru.DmN.siberia.parser.ctx.ParsingContext
import ru.DmN.siberia.processor.Processor
import ru.DmN.siberia.processor.ctx.ProcessingContext
import ru.DmN.siberia.processor.utils.module
import ru.DmN.siberia.processor.utils.platform
import ru.DmN.siberia.processors.NRProgn
import ru.DmN.siberia.utils.IPlatform.UNIVERSAL
import ru.DmN.siberia.utils.node.NodeTypes.PROGN
import ru.DmN.siberia.utils.vtype.VirtualType
import java.util.*
import ru.DmN.pht.processor.ctx.macros as macros_list
import ru.DmN.pht.processors.NRProgn as NRPrognA

object Pihta : ModuleCompilers("pht", UNIVERSAL) {
    private fun initParsers() {
        // a
        addSNP(ADD)
        addSNP(AGET)
        addSNP(ALIAS_TYPE)
        addSNP(ALIAS_MACRO)
        addSNP(AND)
        addSNP(APP)
        addSNP(APP_FN)
        addSNP(ARR_OF)
        addSNP(ARR_OF_TYPE)
        addSNP(ARR_SIZE)
        addSNP(ARR_TYPE)
        addSNP(AS)
        addSNP(ASET)
        // b
        addSNP(BGET)
        addSNP(BSET)
        addSNP(BLOCK)
        addSNP(BREAK)
        // c
        addSNP(CATCH)
        addSNP(CCALL)
        addSNP(CLR_NULL_TYPE)
        addSNP(CLS)
        addSNP(COMP_TYPE)
        addNP("comment",      NPComment)
        addSNP(COND)
        addSNP(CONTINUE)
        addSNP(CTOR)
        addSNP(CYCLE)
        // d
        addNP("debug",        NPDebug)
        addSNP(DEC_PRE)
        addSNP(DEC_POST)
        addSNP(DEF)
        addSNP(DEF_FLD)
        addSNP(DEF_SET)
        addNP("def-macro",    NPDefMacro)
        addSWMNP(DEFN)
        addSNP(DIV)
        // e
        addSNP(ECTOR)
        addSNP(EFLD)
        addSWMNP(EFN)
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
        addNP("get-or-name!", NPGetOrName)
        addSNP(GFN)
        addSNP(GREAT)
        addSNP(GREAT_OR_EQ)
        // i
        addSNP(IF)
        addSNP(IMPORT)
        addSNP(INC_PRE)
        addSNP(INC_POST)
        addNP("inc-pht",      NPIncPht)
        addNP("inl-body",     NPInlBody)
        addSNP(INL_DEF)
        addSNP(INNER)
        addSNP(IS)
        addSNP(ITF)
        // l
        addNP("lazy-symbol",  NPLazySymbol)
        addSNP(LESS)
        addSNP(LESS_OR_EQ)
        addSNP(LIST_OF)
        // m
        addNP("macro",        NPMacroA)
        addNP("macro!",       NPMacroB)
        addNP("macro-arg",    NPMacroArg)
        addNP("macro-inline", NPMacroInline)
        addNP("macro-unroll", NPMacroUnroll)
        addSNP(MCALL)
        addNP("mcall!",       NPMCallB)
        addSNP(MUL)
        // n
        addSNP(NB)
        addSNP(NEG)
        addSNP(NEW)
        addSNP(NEW_ARR)
        addSNP(NOT)
        addSNP(NOT_EQ)
        addSNP(NOT_NULL_TYPE)
        addSNP(NS)
        addSNP(NULL_TYPE)
        // o
        addSNP(OBJ)
        addSNP(OR)
        addSNP(OVER_SET_PRE)
        addSNP(OVER_SET_POST)
        // p
        addSNP(PRINT)
        addSNP(PRINTLN)
        addNP("progn-",       NPPrognB)
        // r
        addSNP(RAND_SYMBOL)
        addSNP(RANGE)
        addSNP(REM)
        addSNP(RET)
        addSNP(RFN)
        // s
        addNP("set",          NPSetA)
        addNP("set!",         NPSetB)
        addSNP(SHIFT_LEFT)
        addSNP(SHIFT_RIGHT)
        addSNP(SUB)
        addSNP(SYMBOL)
        addSNP(SYMBOL_INT)
        // t
        addSNP(TEST_FN)
        addSNP(THROW)
        addSNP(TRCALL)
        addSNP(TGET)
        addSNP(TO_EFN)
        addSNP(TYPE_OF)
        // u
        addNP("unit",         NPUnit)
        addSNP(UNPARSE)
        addSNP(UNREALIZED)
        // v
        addSNP(VALN)
        addNP("valn!",        NPValnB)
        addSNP(VALN_REPEAT)
        addSNP(VALN_SIZE)
        addNP("value",        NPValueA)
        addNP("value!",       NPValueB)
        // w
        addSNP(WITH_GENS)
        // x
        addSNP(XOR)
        // y
        addSNP(YIELD)

        // @
        addSMNP(ANN_ABSTRACT)
        addSMNP(ANN_FILE)
        addSMNP(ANN_FINAL)
        addSMNP(ANN_INLINE)
        addSMNP(ANN_OPEN)
        addSMNP(ANN_STATIC)
        addSMNP(ANN_TEST)
        addSMNP(ANN_TO_EFN)
        addSMNP(ANN_VARARGS)

        // @@
        addNP("@@if-platform", NPIfPlatform)
        addSNP(CT_ROLL_LEFT)
        addSNP(CT_ROLL_RIGHT)
        addNP("@@skip",        NPSkip)

        // *
        addSNP(CTC_MODULE_NAME)
        addSNP(CTC_TYPE_NAME)
        addSNP(CTC_FN_NAME)
        addSNP(CTC_NS_NAME)

        // Длинные аналоги

        // a
        "arr-get"       to "aget"
        "app-class"     to "app"
        "app-func"      to "app-fn"
        "arr-set"       to "aset"
        // b
        "block-get"     to "bget"
        "block-set"     to "bset"
        // c
        "ctor-call"     to "ccall"
        "class"         to "cls"
        "condition"     to "cond"
        "def-ctor"      to "ctor"
        // d
        "def-or-set"    to "def-set"
        "def-func"      to "defn"
        // e
        "enum-ctor"     to "ector"
        "enum-fld"      to "efld"
        "def-ext-func"  to "efn"
        // f
        "fld-get"       to "fget"
        "field"         to "field"
        "lambda"        to "fn"
        "fld-set"       to "fset"
        // g
        "def-gen-func"  to "gfn"
        // i
        "interface"     to "itf"
        // m
        "method-call"   to "mcall"
        // n
        "named-block"   to "nb"
        "namespace"     to "ns"
        // o
        "object"        to "obj"
        "oset"          to "over-set"
        "osetp"         to "over-set-post"
        // r
        "return"        to "ret"
        "ref-func"      to "rfn"
        // t
        "test-func"     to "test-fn"
        "tailrec-call"  to "trcall"
        "typed-get"     to "tget"
        "to-ext-func"   to "fo-efn"
        // @
        "@to-ext-func"  to "@to-efn"

        // Символьные аналоги

        // a
        "+"  to "add"
        "&"  to "and"
        // d
        "--" to "dec"
        "/"  to "div"
        // e
        "="  to "eq"
        // g
        ">"  to "great"
        ">=" to "gt-or-eq"
        // i
        "++" to "inc"
        // l
        "<"  to "less"
        "<=" to "ls-or-eq"
        // m
        "*"  to "mul"
        // n
        "!"  to "not"
        "!=" to "not-eq"
        // o
        "|"  to "or"
        // r
        "%"  to "rem"
        "<-" to "@@roll-left"
        "->" to "@@roll-right"
        // s
        "<<" to "shift-left"
        ">>" to "shift-right"
        "-"  to "sub"
    }

    private infix fun String.to(alias: String) {
        add(this.toRegularExpr(), NPNodeAlias(alias))
    }

    private fun initUnparsers() {
        // a
        addSNU(ADD)
        addSNU(ADD_)
        addSNU(AGET)
        add(AGET_,          NUAGet)
        addSNU(ALIAS_TYPE)
        add(ALIAS_TYPE_,    NUAliasType)
        addSNU(ALIAS_MACRO)
        addSNU(AND)
        addSNU(AND_)
        addSNU(APP)
        addSNU(APP_FN)
        addSNU(ARR_OF)
        addSNU(ARR_OF_TYPE)
        addSNU(ARR_SIZE)
        addSNU(ARR_SIZE_)
        addSNU(ARR_TYPE)
        addSNU(AS)
        add(AS_,            NUIsAs)
        addSNU(ASET)
        addSNU(ASET_)
        // b
        addSNU(BGET)
        add(BGET_,          NUBGet)
        addSNU(BSET)
        add(BSET_,          NUBSet)
        addSNU(BLOCK)
        addSNU(BLOCK_)
        addSNU(BREAK)
        add(BREAK_,         NUNamedBlock)
        // c
        addSNU(CATCH)
        add(CATCH_,         NUCatch)
        addSNU(CCALL)
        addSNU(CCALL_)
        addSNU(CLR_NULL_TYPE)
        addSNU(CLS)
        addSNU(COMP_TYPE)
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
        addSNU(DEC_POST)
        addSNU(DEF)
        add(DEF_,           NUDef)
        addSNU(DEF_FLD)
        add(DEF_FLD_,       NUFld)
        addSNU(DEF_SET)
        addSNU(DEF_MACRO)
        addSNU(DEFN)
        add(DEFN_,          NUDefn)
        addSNU(DIV)
        addSNU(DIV_)
        // e
        addSNU(ECTOR)
        add(ECTOR_,         NUECtor)
        addSNU(EFLD)
        add(EFLD_,          NUEFld)
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
        add(FN_,            NUFn)
        addSNU(FOR)
        addSNU(FSET_A)
        add(FSET_B,         NUFSetA)
        add(FSET_,          NUFSetB)
        // g
        addSNU(GET)
        add(GET_,           NUGet)
        add(GET_OR_NAME,    NUGetOrName)
        addSNU(GFN)
        add(GFN_,           NUGFn)
        addSNU(GREAT)
        addSNU(GREAT_)
        addSNU(GREAT_OR_EQ)
        addSNU(GREAT_OR_EQ_)
        // i
        addSNU(IF)
        addSNU(IF_)
        addSNU(IMPORT)
        add(IMPORT_,        NUImport)
        addSNU(INC_PRE)
        addSNU(INC_POST)
        addSNU(INC_PHT)
        add(INL_BODY_,      NUInlBody)
        addSNU(INL_DEF)
        // INL_DEF_
        addSNU(INNER)
        add(INNER_,         NUInner)
        addSNU(IS)
        add(IS_,            NUIsAs)
        addSNU(ITF)
        add(ITF_,           NUClass)
        // l
        addSNU(LAZY_SYMBOL)
        addSNU(LESS)
        addSNU(LESS_)
        addSNU(LESS_OR_EQ)
        addSNU(LESS_OR_EQ_)
        addSNU(LIST_OF)
        // m
        add(MACRO_A,        NUMacro)
        add(MACRO_B,        NUMacro)
        addSNU(MACRO_ARG)
        addSNU(MACRO_INLINE)
        addSNU(MACRO_UNROLL)
        addSNU(MCALL)
        add(MCALL_,         NUMCall)
        addSNU(MUL)
        addSNU(MUL_)
        // n
        add(NAME,           NUGetOrName)
        addSNU(NB)
        add(NB_,            NUNamedBlock)
        addSNU(NEG)
        addSNU(NEG_)
        addSNU(NEW)
        add(NEW_,           NUNew)
        addSNU(NEW_ARR)
        add(NEW_ARR_,       NUNewArray)
        addSNU(NOT)
        addSNU(NOT_)
        addSNU(NOT_EQ)
        addSNU(NOT_EQ_)
        addSNU(NOT_NULL_TYPE)
        addSNU(NS)
        add(NS_,            NUNs)
        addSNU(NULL_TYPE)
        // o
        addSNU(OBJ)
        add(OBJ_,           NUClass)
        addSNU(OR)
        addSNU(OR_)
        addSNU(OVER_SET_PRE)
        addSNU(OVER_SET_POST)
        // p
        addSNU(PRINT)
        addSNU(PRINT_)
        addSNU(PRINTLN)
        addSNU(PRINTLN_)
        addSNU(PROGN_B)
        addSNU(PROGN_B_)
        // r
        addSNU(RAND_SYMBOL)
        addSNU(RANGE)
        addSNU(REM)
        addSNU(REM_)
        addSNU(RET)
        addSNU(RET_)
        addSNU(RFN)
        add(RFN_,           NURFn)
        // s
        addSNU(SET_A)
        add(SET_B,          NUSetB)
        add(SET_,           NUSetB)
        addSNU(SHIFT_LEFT)
        addSNU(SHIFT_LEFT_)
        addSNU(SHIFT_RIGHT)
        addSNU(SHIFT_RIGHT_)
        addSNU(SUB)
        addSNU(SUB_)
        addSNU(SYMBOL)
        addSNU(SYMBOL_INT)
        // t
        addSNU(TEST_FN)
        addSNU(THROW)
        addSNU(THROW_)
        addSNU(TRCALL)
        addSNU(TRCALL_)
        addSNU(TGET)
        add(TGET_,          NUTGet)
        addSNU(TO_EFN)
        add(TO_EFN_,        NUToEFn)
        addSNU(TYPE_OF)
        // u
        add(UNIT,           NUUnit)
        addSNU(UNPARSE)
        addSNU(UNREALIZED)
        // v
        add(VALN,           NUValn)
        add(VALN_,          NUValn)
        addSNU(VALN_REPEAT)
        addSNU(VALN_SIZE)
        add(VALUE,          NUValue)
        // w
        addSNU(WITH_GENS)
        // x
        addSNU(XOR)
        addSNU(XOR_)
        // y
        addSNU(YIELD)

        // @
        addSNU(ANN_ABSTRACT)
        addSNU(ANN_ABSTRACT_)
        addSNU(ANN_FILE)
        addSNU(ANN_FILE_)
        addSNU(ANN_FINAL)
        addSNU(ANN_FINAL_)
        addSNU(ANN_INLINE)
        addSNU(ANN_INLINE_)
        addSNU(ANN_OPEN)
        addSNU(ANN_OPEN_)
        addSNU(ANN_STATIC)
        addSNU(ANN_STATIC_)
        addSNU(ANN_TEST)
        addSNU(ANN_TEST_)
        addSNU(ANN_TO_EFN)
        addSNU(ANN_VARARGS)
        addSNU(ANN_VARARGS_)

        // @@
        add(CT_IF_PLATFORM, NUIfPlatform)
        addSNU(CT_ROLL_LEFT)
        addSNU(CT_ROLL_RIGHT)
        add(CT_SKIP,        NUSkip)

        // *
        addSNU(CTC_MODULE_NAME)
        addSNU(CTC_TYPE_NAME)
        addSNU(CTC_FN_NAME)
        addSNU(CTC_NS_NAME)
    }

    private fun initProcessors() {
        // a
        add(ADD,           NRMath)
        add(ADD_,          NRMathB)
        add(ALIAS_TYPE,    NRAliasType)
        add(ALIAS_MACRO,   NRAliasMacro)
        add(AGET,          NRAGet)
        add(AGET_,         NRAGetB)
        add(AND,           NRMath)
        add(AND_,          NRMathB)
        add(APP,           NRApp)
        add(APP_FN,        NRAppFn)
        add(ARR_OF,        NRArrayOf)
        add(ARR_OF_TYPE,   NRArrayOfType)
        add(ARR_SIZE,      NRArraySize)
        add(ARR_SIZE_,     NRArraySize)
        add(ARR_TYPE,      NRArrayType)
        add(AS,            NRAs)
        add(AS_,           NRAsB)
        add(ASET,          NRASet)
        add(ASET_,         NRASet)
        // b
        add(BGET,          NRBGet)
        add(BGET_,         NRBGetB)
        add(BSET,          NRBSet)
        add(BLOCK,         NRBody)
        add(BLOCK_,        NRBody)
        add(BREAK,         NRBreakContinue)
        //
        add(CATCH,         NRCatch)
        add(CATCH_,        NRCatchB)
        add(CCALL,         NRCCall)
        add(CLR_NULL_TYPE, NRClearNullType)
        add(CLS,           NRClass)
        add(COMP_TYPE,     NRComponentType)
        add(COND,          NRCond)
        add(CONTINUE,      NRBreakContinue)
        add(CTOR,          NRCtor)
        add(CYCLE,         NRCycle)
        add(CYCLE_,        NRVoid)
        // d
        add(DEBUG,         NRDebug)
        add(DEC_PRE,       NRIncDec)
        add(DEC_POST,      NRIncDec)
        add(DEF,           NRDef)
        add(DEF_FLD,       NRDefFld)
        add(DEF_SET,       NRDefSet)
        add(DEF_MACRO,     NRDefMacro)
        add(DEFN,          NRDefn)
        add(DIV,           NRMath)
        add(DIV_,          NRMathB)
        // e
        add(ECTOR,         NRECtor)
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
        add(GET,           NRGetB)
        add(GET_,          NRGetC)
        add(GET_OR_NAME,   NRGetOrName)
        add(GFN,           NRGFn)
        add(GREAT,         NRCompare)
        add(GREAT_,        NRCompareB)
        add(GREAT_OR_EQ,   NRCompare)
        add(GREAT_OR_EQ_,  NRCompareB)
        // i
        add(IF,            NRIf)
        add(IF_,           NRIfB)
        add(IMPORT,        NRImport)
        add(INC_PRE,       NRIncDec)
        add(INC_POST,      NRIncDec)
        add(INC_PHT,       NRIncPht)
        add(INL_BODY_A,    NRInlBodyA)
        add(INL_BODY_B,    NRInlBodyB)
        add(INL_BODY_,     NRInlBodyA)
        add(INL_DEF,       NRInlDef)
        add(INNER,         NRInner)
        add(IS,            NRIs)
        add(IS_,           NRIs)
        add(ITF,           NRClass)
        // l
        add(LAZY_SYMBOL,   NRLazySymbol)
        add(LESS,          NRCompare)
        add(LESS_,         NRCompareB)
        add(LESS_OR_EQ,    NRCompare)
        add(LESS_OR_EQ_,   NRCompareB)
        add(LIST_OF,       NRListOf)
        // m
        add(MACRO_A,       NRMacro)
        add(MACRO_B,       NRMacro)
        add(MACRO_ARG,     NRMacroArg)
        add(MACRO_INLINE,  NRMacroInline)
        add(MACRO_UNROLL,  NRMacroUnroll)
        add(MCALL,         NRMCall)
        add(MCALL_,        NRMCallB)
        add(MUL,           NRMath)
        add(MUL_,          NRMathB)
        // n
        add(NAME,          NRGetOrName)
        add(NB,            NRNamedList)
        add(NB_,           NRProgn)
        add(NEG,           NRMath)
        add(NEG_,          NRMathB)
        add(NEW,           NRNew)
        add(NEW_,          NRNewB)
        add(NEW_ARR,       NRNewArray)
        add(NEW_ARR_,      NRNewArrayB)
        add(NOT,           NRNot)
        add(NOT_,          NRCompareB)
        add(NOT_EQ,        NRCompare)
        add(NOT_EQ_,       NRCompareB)
        add(NOT_NULL_TYPE, NRNotNullType)
        add(NS,            NRNs)
        add(NS_,           NRNs)
        add(NULL_TYPE,     NRNullableType)
        // o
        add(OBJ,           NRObj)
        add(OBJ_,          NRObjB)
        add(OR,            NRMath)
        add(OR_,           NRMathB)
        add(OVER_SET_PRE,  NROverSetPre)
        add(OVER_SET_POST, NROverSetPost)
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
        add(RET_,          NRRet)
        add(RFN,           NRRFn)
        add(RFN_,          NRRFnB)
        // s
        add(SET_B,         NRSet)
        add(SHIFT_LEFT,    NRMath)
        add(SHIFT_LEFT_,   NRMathB)
        add(SHIFT_RIGHT,   NRMath)
        add(SHIFT_RIGHT_,  NRMathB)
        add(SUB,           NRMath)
        add(SUB_,          NRMathB)
        add(SYMBOL,        NRSymbol)
        add(SYMBOL_INT,    NRSymbolInt)
        // t
        add(TEST_FN,       NRTestFn)
        add(THROW,         NRThrow)
        add(THROW_,        NRThrow)
        add(TRCALL,        NRTRCall)
        add(TRCALL_,       NRTRCall)
        add(TGET,          NRTGet)
        add(TGET_,         NRTGetB)
        add(TO_EFN,        NRToEFn)
        add(TYPE_OF,       NRTypeof)
        // u
        add(UNIT,          NRUnit)
        add(UNPARSE,       NRUnparse)
        add(UNREALIZED,    NRUnrealized)
        // v
        add(VALN,          NRValn)
        add(VALN_,         NRValn)
        add(VALN_REPEAT,   NRValnRepeat)
        add(VALN_SIZE,     NRValnSize)
        add(VALUE,         NRValue)
        // w
        add(WITH_GENS,     NRWithGens)
        // x
        add(XOR,           NRMath)
        add(XOR_,          NRMathB)
        // y
        add(YIELD,         NRYield)

        // @
        add(ANN_ABSTRACT, NRSA { it, _, _ -> it.setMetadata(MetadataKeys.ABSTRACT, true) })
        add(ANN_FILE,     NRSA { it, _, _ -> it.setMetadata(MetadataKeys.FILE,     true) })
        add(ANN_INLINE,   NRSA { it, _, _ -> it.setMetadata(MetadataKeys.INLINE,   true) })
        add(ANN_FINAL,    NRSA { it, _, _ -> it.setMetadata(MetadataKeys.FINAL,    true) })
        add(ANN_OPEN,     NRSA { it, _, _ -> it.setMetadata(MetadataKeys.OPEN,     true) })
        add(ANN_STATIC,   NRSA { it, _, _ -> it.setMetadata(MetadataKeys.STATIC,   true) })
        add(ANN_TEST,     NRSA { it, _, _ -> it.setMetadata(MetadataKeys.TEST,     true) })
        add(ANN_TO_EFN,   NRAnnToEfn)
        add(ANN_VARARGS,  NRSA { it, _, _ -> it.setMetadata(MetadataKeys.VARARG,   true) })

        // @@
        add(CT_IF_PLATFORM, NRIfPlatform)
        add(CT_ROLL_LEFT,   NRRollLeft)
        add(CT_ROLL_RIGHT,  NRRollRight)
        add(CT_SKIP,        NRSkip)

        // *
        add(CTC_MODULE_NAME, NRCTSC { _, ctx -> ctx.module.name })
        add(CTC_TYPE_NAME,   NRCTSC { _, ctx -> ctx.clazz.name })
        add(CTC_FN_NAME,     NRCTSC { _, ctx -> ctx.method.name })
        add(CTC_NS_NAME,     NRCTSC { _, ctx -> ctx.global.namespace })
    }

    private fun initCompilers() {
        // i
        add(IMPORT_,       NCUnit)
        // p
        add(PROGN_B_,      NCDefault)

        // @
        add(ANN_ABSTRACT_, NCDefault)
        add(ANN_FINAL_,    NCDefault)
        add(ANN_INLINE_,   NCDefault)
        add(ANN_OPEN_,     NCDefault)
        add(ANN_STATIC_,   NCDefault)
        add(ANN_VARARGS_,  NCDefault)

        // @@
        add(CT_SKIP,       NCSkip)
    }

    override fun load(parser: Parser, ctx: ParsingContext, uses: MutableList<String>): Boolean {
        if (!ctx.loadedModules.contains(this)) {
            ctx.macros = Stack()
            // Платформно-зависимые функции
            when (ctx.platform) {
                JVM -> uses += "pht/jvm"
            }
            //
            super.load(parser, ctx, uses)
            return true
        }
        return false
    }

    override fun changeParser(parser: Parser, ctx: ParsingContext): Parser =
        ParserImpl(parser)

    @Suppress("UNCHECKED_CAST")
    override fun load(processor: Processor, ctx: ProcessingContext, uses: MutableList<String>): Boolean {
        if (!ctx.loadedModules.contains(this)) {
            processor.tp += VTAuto
            processor.tp += VTDynamic
            PhtProcessingStage.addStagesAfter(processor.sm)
            processor.contexts.macros_list = HashMap()
            ctx.global = GlobalContext(processor.tp)
            ctx.classes = LinkedClassesNode.LinkedClassesNodeStart as LinkedClassesNode<VirtualType>
            ctx.getType = NRValue::getType
            ctx.cast = NRAs::cast
            ctx.castFrom = NRAs::castFrom
            return super.load(processor, ctx, uses)
        }
        return false
    }
    @Suppress("UNCHECKED_CAST")
    override fun load(compiler: Compiler, ctx: CompilationContext) {
        if (!ctx.loadedModules.contains(this)) {
            ctx.classes = LinkedClassesNode.LinkedClassesNodeStart as LinkedClassesNode<VirtualType>
            super.load(compiler, ctx)
        }
    }

    init {
        initParsers()
        initUnparsers()
        initProcessors()
        initCompilers()
    }
}