package ru.DmN.siberia.lexer

/**
 * Токен
 */
data class Token(
    /**
     * Строка токена
     */
    val line: Int,
    /**
     * Тип токена
     */
    val type: Type,
    /**
     * Текст токена
     */
    val text: String? = null
) {
    /**
     * Создаёт "обработанную" версию токена
     */
    fun processed() =
        Token(line, type, "!$text")

    companion object {
        /**
         * Создаёт токен операции
         */
        fun operation(line: Int, text: String) =
            Token(line, Type.OPERATION, text)
    }

    /**
     * Тип токена
     */
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
