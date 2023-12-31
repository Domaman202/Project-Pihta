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
     * Список всех классов контекста (включая текущий)
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
     * Контекст макроса.
     */
    MACRO,

    /**
     * Список макросов.
     */
    MACROS
}