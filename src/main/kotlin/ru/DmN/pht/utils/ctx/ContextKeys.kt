package ru.DmN.pht.utils.ctx

import ru.DmN.siberia.utils.ctx.IContextKey

enum class ContextKeys : IContextKey {
    /**
     * Глобальный контекст.
     */
    GLOBAL,
    /**
     * Контекст перечисления.
     */
    ENUM,

    /**
     * Класс.
     */
    CLASS,

    /**
     * (Processor)
     * Список классов контекста (исключая текущий).
     */
    CLASSES,

    /**
     * (JVM Compiler)
     * Список скомпилированных классов.
     */
    COMPILED_CLASSES,

    /**
     * Метод.
     */
    METHOD,

    /**
     * Тело.
     */
    BODY,

    /**
     * Список именованных блоков.
     */
    NAMED_BLOCKS,

    /**
     * Хук возврата.
     */
    RETURN_HOOK,

    /**
     * Контекст макроса.
     */
    MACRO,

    /**
     * Список макросов.
     */
    MACROS,

    /**
     * (Processor)
     *
     * Функция получения типа из строки с учётом префиксов.
     */
    F_GET_TYPE,

    /**
     * (Processor)
     *
     * Функция получает ноду и тип к которому нужно привести её значение.
     */
    F_CAST,


    /**
     * (Processor)
     *
     * Функция получает ноду, исходный тип и тип к которому нужно привести её значение.
     */
    F_CAST_FROM
}