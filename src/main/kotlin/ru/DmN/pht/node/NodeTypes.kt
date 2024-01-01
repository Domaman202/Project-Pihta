package ru.DmN.pht.std.node

import ru.DmN.siberia.node.INodeType
import ru.DmN.siberia.node.NodeTypes.Type
import ru.DmN.siberia.node.NodeTypes.Type.PARSED
import ru.DmN.siberia.node.NodeTypes.Type.PROCESSED

enum class NodeTypes : INodeType {
    // a
    ADD_("add", PROCESSED),
    AGET("aget", PARSED),
    AGET_("aget", PROCESSED),
    ALIAS_TYPE("alias-type", PARSED),
    ALIAS_TYPE_("!alias-type", PROCESSED),
    AND_("and", PROCESSED),
    APP("app", true, true),
    APP_FN("app-fn", true, true),
    ARRAY_OF("array-of", PARSED),
    ARRAY_OF_TYPE("array-of-type", PARSED),
    ARRAY_SIZE("array-size", PARSED),
    ARRAY_SIZE_("array-size", PROCESSED),
    ARRAY_TYPE("array-type", PARSED),
    AS_("as", PROCESSED),
    AS_GENS("as-gens", PARSED),
    ASET_("aset", PROCESSED),
    // b
    BODY_("body", PROCESSED),
    BREAK_("break", PROCESSED),
    // c
    CATCH_("catch", PROCESSED),
    CCALL("ccall", PARSED),
    CLS_("cls", PROCESSED),
    COND("cond", PARSED),
    CONTINUE_("continue", PROCESSED),
    CTOR("ctor", PARSED),
    CTOR_("ctor", PROCESSED),
    CYCLE_("cycle", PROCESSED),
    // d
    DEBUG("debug", true, true),
    DEC_PRE_("dec", PROCESSED),
    DEC_POST_("dec-", PROCESSED),
    DEF("def", PARSED),
    DEF_("def", PROCESSED),
    DEF_SET("def-set", PARSED),
    DEFMACRO("defmacro", PARSED),
    DEFN_("defn", PROCESSED),
    DIV_("div", PROCESSED),
    // e
    ECTOR("ector", PARSED),
    EFLD_("efld", PROCESSED),
    EFN_("efn", PROCESSED),
    ENUM("enum", PARSED),
    ENUM_("enum", PROCESSED),
    EQ_("eq", PROCESSED),
    // f
    FGET_("fget", PROCESSED),
    FLD_("fld", PROCESSED),
    FN_("!fn", PROCESSED),
    FOR("for", PARSED),
    FSET_("fset", PROCESSED),
    // g
    GET_A("get", PARSED),
    GET_("get!", PROCESSED),
    GET_OR_NAME("get-or-name!", true, true),
    GREAT_("great", PROCESSED),
    GREAT_OR_EQ_("great-or-eq", PROCESSED),
    // i
    IF("if", PARSED),
    IF_("if", PROCESSED),
    IMPORT("import", PARSED),
    IMPORT_("import", PROCESSED),
    INC_PRE_("inc", PROCESSED),
    INC_POST_("inc-", PROCESSED),
    IS_("is", PROCESSED),
    ITF_("itf", PROCESSED),
    // l
    LAZY_SYMBOL("lazy-symbol", PARSED),
    LESS_("less", PROCESSED),
    LESS_OR_EQ_("less-or-eq", PROCESSED),
    LIST_OF("list-of", PARSED),
    // m
    MACRO("macro", PARSED),
    MACRO_ARG("macro-arg", PARSED),
    MACRO_INLINE("macro-inline", PARSED),
    MACRO_UNROLL("macro-unroll", PARSED),
    MCALL_("mcall", PROCESSED),
    MUL_("mul", PROCESSED),
    // n
    NAMED_BLOCK_("named-block", PROCESSED),
    NEG_("neg", PROCESSED),
    NEW_("new", PROCESSED),
    NEW_ARRAY_("new-array", PROCESSED),
    NOT_("not", PROCESSED),
    NOT_EQ_("not-eq", PROCESSED),
    NS_("ns", PROCESSED),
    // o
    OBJ_("obj", PROCESSED),
    OR_("or", PROCESSED),
    // p
    PRINT("print", PARSED),
    PRINTLN("println", PARSED),
    PROGN_B("progn-", PARSED),
    PROGN_B_("progn-", PROCESSED),
    // r
    RAND_SYMBOL("rand-symbol", PARSED),
    RANGE("range", PARSED),
    REM_("rem", PROCESSED),
    RET_("ret", PROCESSED),
    ROLL_LEFT("roll-left", PARSED),
    ROLL_RIGHT("roll-right", PARSED),
    // s
    SET_A("set", PARSED),
    SET_("set!", PROCESSED),
    SHIFT_LEFT_("shift-left", PROCESSED),
    SHIFT_RIGHT_("shift-right", PROCESSED),
    SUB_("sub", PROCESSED),
    SYMBOL("symbol", PARSED),
    SYMBOL_CLS("symbol-cls", PARSED),
    // t
    TEST_FN("test-fn", PARSED),
    THROW("throw", PARSED),
    THROW_("throw", PROCESSED),
    TYPED_GET("typed-get", PARSED),
    TYPED_GET_("typed-get", PROCESSED),
    TYPEOF("typeof", PARSED),
    // u
    UNIT("unit", PROCESSED),
    // v
    VALN_("valn", PROCESSED),
    VALN_REPEAT("valn-repeat", PARSED),
    VALUE("value", true, true),
    // w
    WITH_GENS("with-gens", PARSED),
    // x
    XOR_("xor", PROCESSED),
    // y
    YIELD("yield", PARSED),

    // @
    ANN_ABSTRACT_("@abstract", PROCESSED),
    ANN_FINAL_("@final", PROCESSED),
    ANN_OPEN_("@open", PROCESSED),
    ANN_STATIC_("@static", PROCESSED),
    ANN_VARARGS_("@varargs", PROCESSED),

    // *
    CTC_MODULE_NAME("*module-name*", PARSED),
    CTC_TYPE_NAME("*type-name*", PARSED),
    CTC_FN_NAME("*fn-name*", PARSED),
    CTC_NS_NAME("*ns-name*", PARSED);

    override val operation: String
    override val processable: Boolean
    override val compilable: Boolean

    constructor(operation: String, processable: Boolean, compilable: Boolean) {
        this.operation = operation
        this.processable = processable
        this.compilable = compilable
    }

    constructor(operation: String, type: Type) {
        this.operation = operation
        if (type == PARSED) {
            this.processable = true
            this.compilable = false
        } else {
            this.processable = false
            this.compilable = true
        }
    }
}