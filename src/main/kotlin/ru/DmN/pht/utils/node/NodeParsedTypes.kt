package ru.DmN.pht.utils.node

import ru.DmN.pht.utils.node.NodeTypes.*
import ru.DmN.siberia.utils.node.INodeType

enum class NodeParsedTypes(override val operation: String, override val processed: INodeType) : IParsedNodeType {
    // a
    ADD("add", ADD_),                           // add          // +
    AND("and", AND_),                           // and          // &
    AS("as", AS_),                              // as
    ASET("aset", ASET_),                        // arr-set
    // b
    BGET("bget", BGET_),                        // block-get
    BLOCK("block", BLOCK_),                     // block
    BREAK("break", BREAK_),                     // break
    // c
    CATCH("catch", CATCH_),                     // catch
    CLS("cls", CLS_),                           // class
    CONTINUE("continue", CONTINUE_),            // continue
    CTOR("ctor", CTOR_),                        // def-ctor
    CYCLE("cycle", CYCLE_),                     // cycle
    DEC_PRE("dec", DEC_PRE_),                   // dec          // --
    DEC_POST("dec-", DEC_POST_),                // dec-
    DEFN("defn", DEFN_),                        // def-func
    DIV("div", DIV_),                           // div          // /
    // e
    ECTOR("ector", ECTOR_),                     // def-enum-ctor
    EFLD("efld", EFLD_),                        // enum-fld
    EFN("efn", EFN_),                           // def-ext-func
    EQ("eq", EQ_),                              // eq           // =
    // f
    FGET_A("fget", FGET_),                      // fld-get
    FGET_B("fget", FGET_),                      // fld-get
    FN("fn", FN_),                              // lambda
    FSET_A("fset", FSET_),                      // fld-set
    FSET_B("fset", FSET_),                      // fld-set
    // g
    GET("get", GET_),                           // get
    GREAT("great", GREAT_),                     // great        // >
    GREAT_OR_EQ("gt-or-eq", GREAT_OR_EQ_),      // gt-or-eq     // >=
    // i
    INC_PRE("inc", INC_PRE_),                   // inc // ++
    INC_POST("inc-", INC_POST_),                // inc-
    IS("is", IS_),                              // is
    ITF("itf", ITF_),                           // interface
    // l
    LESS("less", LESS_),                        // less         // <
    LESS_OR_EQ("ls-or-eq", LESS_OR_EQ_),        // ls-or-eq     // <=
    // m
    MCALL("mcall", MCALL_),                     // method-call
    MUL("mul", MUL_),                           // mul          // *
    // n
    NBLOCK("nblock", NBLOCK_),                  // named-block
    NEG("neg", NEG_),                           // neg
    NEW("new", NEW_),                           // new
    NEW_ARR("new-arr", NEW_ARR_),               // new-arr
    NOT("not", NOT_),                           // not          // !
    NOT_EQ("not-eq", NOT_EQ_),                  // not-eq       // !=
    NS("ns", NS_),                              // ns
    // o
    OBJ("obj", OBJ_),                           // object
    OR("or", OR_),                              // or           // |
    // p
    PRINT("print", PRINT_),                     // print
    PRINTLN("println", PRINTLN_),               // println
    // r
    REM("rem", REM_),                           // rem
    RET("ret", RET_),                           // ret
    // s
    SET_B("set!", SET_),                        // set
    SHIFT_LEFT("shift-left", SHIFT_LEFT_),      // shift-left   // <<
    SHIFT_RIGHT("shift-right", SHIFT_RIGHT_),   // shift-right  // >>
    SUB("sub", SUB_),                           // sub          // -
    // v
    VALN("valn", VALN_),                        // valn
    // x
    XOR("xor", XOR_),                           // xor

    // @
    ANN_ABSTRACT("@abstract", ANN_ABSTRACT_),   // @abstract
    ANN_FILE("@file", ANN_FILE_),               // @file
    ANN_FINAL("@final", ANN_FINAL_),            // @final
    ANN_INLINE("@inline", ANN_INLINE_),         // @inline
    ANN_OPEN("@open", ANN_OPEN_),               // @open
    ANN_STATIC("@static", ANN_STATIC_),         // @static
    ANN_TEST("@test", ANN_TEST_),               // @test
    ANN_VARARGS("@varargs", ANN_VARARGS_);      // @varargs

    override val processable: Boolean
        get() = true
    override val compilable: Boolean
        get() = false
}