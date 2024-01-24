package ru.DmN.pht.ctx

import ru.DmN.siberia.ctx.IContextKey

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
     *
     * (Compiler)
     * Список скомпилированных классов.
     */
    CLASSES,

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
    MACROS
}