package ru.DmN.pht.utils.node

import ru.DmN.siberia.utils.node.INodeType
import ru.DmN.siberia.utils.node.NodeTypes.Type
import ru.DmN.siberia.utils.node.NodeTypes.Type.PARSED
import ru.DmN.siberia.utils.node.NodeTypes.Type.PROCESSED

enum class NodeTypes : INodeType {
    // a
    ADD_("add", PROCESSED),                             // add              // +
    AGET("aget", PARSED),                               // arr-get
    AGET_("aget", PROCESSED),                           // arr-get
    ALIAS_TYPE("alias-type", PARSED),                   // alias-type
    ALIAS_TYPE_("alias-type", PROCESSED),               // alias-type
    AND_("and", PROCESSED),                             // and              // &
    APP("app", true, true),        // app-class
    APP_FN("app-fn", true, true),  // app-func
    ARR_OF("arr-of", PARSED),                           // arr-of
    ARR_OF_TYPE("arr-of-type", PARSED),                 // arr-of-type
    ARR_SIZE("arr-size", PARSED),                       // arr-size
    ARR_SIZE_("arr-size", PROCESSED),                   // arr-size
    ARR_TYPE("arr-type", PARSED),                       // arr-type
    AS_("as", PROCESSED),                               // as
    ASET_("aset", PROCESSED),                           // arr-set
    // b
    BGET_("bget", PROCESSED),                           // block-get
    BSET_("bset", PROCESSED),                           // block-set
    BLOCK_("block", PROCESSED),                         // block
    BREAK_("break", PROCESSED),                         // break
    // c
    CATCH_("catch", PROCESSED),                         // catch
    CCALL("ccall", PARSED),                             // ctor-call
    CCALL_("ccall", PROCESSED),                         // ctor-call
    CLR_NULL_TYPE("clr-null-type", PARSED),             // clr-null-type
    CLS_("cls", PROCESSED),                             // class
    COMP_TYPE("comp-type", PARSED),                     // comp-type
    COND("cond", PARSED),                               // condition
    CONTINUE_("continue", PROCESSED),                   // continue
    CTOR_("ctor", PROCESSED),                           // def-ctor
    CYCLE_("cycle", PROCESSED),                         // cycle
    // d
    DEBUG("debug", true, true),    // debug
    DEC_PRE_("dec", PROCESSED),                         // dec              // --
    DEC_POST_("dec-", PROCESSED),                       // dec-
    DEF("def", PARSED),                                 // def
    DEF_("def", PROCESSED),                             // def
    DEF_SET("def-set", PARSED),                         // def-or-set
    DEF_MACRO("def-macro", PARSED),                     // def-macro
    DEFN_("defn", PROCESSED),                           // def-func
    DIV_("div", PROCESSED),                             // div              // /
    // e
    ECTOR_("ector", PROCESSED),                         // enum-ctor
    EFLD_("efld", PROCESSED),                           // enum-fld
    EFN_("efn", PROCESSED),                             // def-ext-func
    ENUM("enum", PARSED),                               // enum
    ENUM_("enum", PROCESSED),                           // enum
    EQ_("eq", PROCESSED),                               // eq               // =
    // f
    FGET_("fget", PROCESSED),                           // fld-get
    FLD("fld", PARSED),                                 // field
    FLD_("fld", PROCESSED),                             // field
    FN_("fn", PROCESSED),                               // lambda
    FOR("for", PARSED),                                 // for
    FSET_("fset", PROCESSED),                           // fld-set
    // g
    GET_("get", PROCESSED),                             // get
    GET_OR_NAME("get-or-name!", PARSED),                // get-or-name
    GFN("gfn", true, false),       // def-gen-func
    GFN_("gfn", false, false),     // def-gen-func
    GREAT_("great", PROCESSED),                         // great
    GREAT_OR_EQ_("gt-or-eq", PROCESSED),                // gt-or-eq
    // i
    IF("if", PARSED),                                   // if
    IF_("if", PROCESSED),                               // if
    IMPORT("import", PARSED),                           // import
    IMPORT_("import", PROCESSED),                       // import
    INC_PRE_("inc", PROCESSED),                         // inc              // ++
    INC_POST_("inc-", PROCESSED),                       // inc-
    INC_PHT("inc-pht", PARSED),                         // include-pihta
    INL_BODY_A("inl-body", PARSED),                     // inl-body
    INL_BODY_B("inl-body", PARSED),                     // inl-body
    INL_BODY_("inl-body", PROCESSED),                   // inl-body
    INL_DEF("inl-def", PARSED),                         // inl-def
    INNER("inner", PARSED),                             // inner
    INNER_("inner", PROCESSED),                         // inner
    IS_("is", PROCESSED),                               // is
    ITF_("itf", PROCESSED),                             // interface
    // l
    LAZY_SYMBOL("lazy-symbol", PARSED),                 // lazy-symbol
    LESS_("less", PROCESSED),                           // less
    LESS_OR_EQ_("ls-or-eq", PROCESSED),                 // ls-or-eq
    LIST_OF("list-of", PARSED),                         // list-of
    // m
    MACRO_A("macro", PARSED),                           // macro
    MACRO_B("macro", PARSED),                           // macro!
    MACRO_ARG("macro-arg", PARSED),                     // macro-arg
    MACRO_INLINE("macro-inline", PARSED),               // macro-inline
    MACRO_UNROLL("macro-unroll", PARSED),               // macro-unroll
    MCALL_("mcall", PROCESSED),                         // method-call
    MUL_("mul", PROCESSED),                             // mul              // *
    // n
    NAME("name", PROCESSED),                            // name
    NB_("nb", PROCESSED),                               // named-block
    NEG_("neg", PROCESSED),                             // neg
    NEW_("new", PROCESSED),                             // new
    NEW_ARR_("new-arr", PROCESSED),                     // new-arr
    NOT_("not", PROCESSED),                             // not
    NOT_EQ_("not-eq", PROCESSED),                       // not-eq
    NOT_NULL_TYPE("not-null-type", PARSED),             // not-null-type
    NS_("ns", PROCESSED),                               // ns
    NULL_TYPE("null-type", PARSED),                     // null-type
    // o
    OBJ_("obj", PROCESSED),                             // object
    OR_("or", PROCESSED),                               // or               // |
    // p
    PRINT_("print", PROCESSED),                         // print
    PRINTLN_("println", PROCESSED),                     // println
    PROGN_B("progn-", PARSED),                          // progn-
    PROGN_B_("progn-", PROCESSED),                      // progn-
    // r
    RAND_SYMBOL("rand-symbol", PARSED),                 // random-symbol
    RANGE("range", PARSED),                             // range
    REM_("rem", PROCESSED),                             // rem
    RET_("ret", PROCESSED),                             // return
    RFN("rfn", PARSED),                                 // ref-func
    RFN_("rfn", PROCESSED),                             // ref-func
    // s
    SET_A("set", PARSED),                               // set
    SET_("set!", PROCESSED),                            // set
    SHIFT_LEFT_("shift-left", PROCESSED),               // shift-left       // <<
    SHIFT_RIGHT_("shift-right", PROCESSED),             // shift-right      // >>
    SUB_("sub", PROCESSED),                             // sub              // -
    SYMBOL("symbol", PARSED),                           // symbol
    SYMBOL_INT("symbol-int", PARSED),                   // symbol-int
    // t
    TEST_FN("test-fn", PARSED),                         // test-func
    THROW("throw", PARSED),                             // throw
    THROW_("throw", PROCESSED),                         // throw
    TRCALL("trcall", PARSED),                           // tailrec-call
    TRCALL_("trcall", PROCESSED),                       // tailrec-call
    TGET("tget", PARSED),                               // typed-get
    TGET_("tget", PROCESSED),                           // typed-get
    TYPE_OF("type-of", PARSED),                         // type-of
    // u
    UNIT("unit", PROCESSED),                            // unit
    UNPARSE("unparse", PARSED),                         // unparse
    UNREALIZED("unrealized", PARSED),                   // unrealized
    // v
    VALN_("valn", PROCESSED),                           // valn
    VALN_REPEAT("valn-repeat", PARSED),                 // valn-repeat
    VALN_SIZE("valn-size", PARSED),                     // valn-size
    VALUE("value", true, true),     // value
    // w
    WITH_GENS("with-gens", PARSED),                     // with-gens
    // x
    XOR_("xor", PROCESSED),                             // xor
    // y
    YIELD("yield", PARSED),                             // yield

    // @
    ANN_ABSTRACT_("@abstract", PROCESSED),              // @abstract
    ANN_FILE_("@file", PROCESSED),                      // @file
    ANN_FINAL_("@final", PROCESSED),                    // @final
    ANN_INLINE_("@inline", PROCESSED),                  // @inline
    ANN_OPEN_("@open", PROCESSED),                      // @open
    ANN_STATIC_("@static", PROCESSED),                  // @static
    ANN_TEST_("@test", PROCESSED),                      // @test
    ANN_VARARGS_("@varargs", PROCESSED),                // @varargs

    // @@
    CT_IF_PLATFORM("@@if-platform", PARSED),            // @@if-platform
    CT_ROLL_LEFT("@@roll-left", PARSED),                // @@roll-left      // <-
    CT_ROLL_RIGHT("@@roll-right", PARSED),              // @@roll-right     // ->
    CT_SKIP("@@skip", true, true),  // @@skip

    // *
    CTC_MODULE_NAME("*module-name*", PARSED),           // *module-name*
    CTC_TYPE_NAME("*type-name*", PARSED),               // *type-name*
    CTC_FN_NAME("*fn-name*", PARSED),                   // *function-name*
    CTC_NS_NAME("*ns-name*", PARSED);                   // *namespace-name*

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