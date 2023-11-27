package ru.DmN.siberia.utils

/**
 * Коллекция контекстов.
 */
interface IContextCollection<T : IContextCollection<T>> {
    /**
     * Контексты.
     */
    val contexts: MutableMap<String, Any?>

    /**
     * Создаёт под-коллекцию с новым контекстом.
     *
     * @param name Имя нового контекста.
     * @param ctx Новый контекст.
     */
    fun with(name: String, ctx: Any?): T
}