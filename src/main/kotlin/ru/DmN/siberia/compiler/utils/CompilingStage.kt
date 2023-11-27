package ru.DmN.siberia.compiler.utils

/**
 * Стадии компиляции.
 */
enum class CompilingStage {
    /**
     * Неизвестная.
     */
    UNKNOWN,

    /**
     * Предопределение типов.
     */
    TYPES_PREDEFINE,

    /**
     * Определение типов.
     */
    TYPES_DEFINE,

    /**
     * Тела методов.
     */
    METHODS_BODY
}