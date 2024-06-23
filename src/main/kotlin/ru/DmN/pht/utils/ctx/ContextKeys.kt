package ru.DmN.pht.utils.ctx

import ru.DmN.siberia.utils.ctx.IContextKey

enum class ContextKeys : IContextKey {
    /**
     * Глобальный контекст.
     */
    GLOBAL,

    /**
     * (Compiler)
     * Пространство имён.
     */
    NAMESPACE,

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
     * (JVM Compiler)
     * Список скомпилированных классов.
     */
    CLASSES,

    /**
     * Метод.
     */
    METHOD,

    /**
     * (C++ Compiler)
     * Количество тестов.
     */
    TESTS,

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
     * (C++ Compiler)
     * Выхлоп компиляции.
     */
    OUTPUT,

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