package ru.DmN.pht.base.lexer

data class Token(val line: Int, val type: Type, val text: String? = null) {
    enum class Type {
        OPEN_BRACKET,
        CLOSE_BRACKET,
        OPEN_CBRACKET,
        CLOSE_CBRACKET,

        OPERATION,

        PRIMITIVE,
        CLASS,
        NAMING,

        NIL,
        BOOLEAN,
        CHAR,
        INTEGER,
        LONG,
        FLOAT,
        DOUBLE,
        STRING,
    }
}
