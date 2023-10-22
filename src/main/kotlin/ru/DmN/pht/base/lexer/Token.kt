package ru.DmN.pht.base.lexer

data class Token(val line: Int, val type: Type, val text: String? = null) {
    fun processed() =
        Token(line, type, "!$text")

    companion object {
        fun operation(line: Int, text: String) =
            Token(line, Type.OPERATION, text)
    }

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
