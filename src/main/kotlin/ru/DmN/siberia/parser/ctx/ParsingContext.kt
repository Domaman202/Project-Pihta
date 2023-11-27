package ru.DmN.siberia.parser.ctx

import ru.DmN.siberia.parser.utils.parsersPool
import ru.DmN.siberia.utils.IContextCollection
import ru.DmN.siberia.utils.Module
import ru.DmN.pht.std.compiler.java.utils.SubList
import ru.DmN.pht.std.compiler.java.utils.SubMap
import java.util.*
import kotlin.collections.HashMap

/**
 * Контекст парсинга
 */
class ParsingContext (
    /**
     * Загруженные модули
     */
    val loadedModules: MutableList<Module> = ArrayList(),
    /**
     * Контексты
     */
    override val contexts: MutableMap<String, Any?> = HashMap()
) : IContextCollection<ParsingContext> {
    /**
     * Создаёт под-контекст
     */
    fun subCtx() =
        ParsingContext(SubList(loadedModules), SubMap(contexts))

    /**
     * Создаёт под-контекст с общими модульными зависимостями.
     * Добавляет новый элемент контекста.
     *
     * @param name Имя нового элемента контекста.
     * @param ctx Новый элемент контекста.
     */
    override fun with(name: String, ctx: Any?): ParsingContext =
        ParsingContext(SubList(loadedModules), SubMap(contexts).apply { this[name] = ctx })

    companion object {
        /**
         * Создаёт базовый контекст.
         */
        fun base() =
            ParsingContext(mutableListOf(ru.DmN.siberia.Siberia)).apply { this.parsersPool = Stack() }

        /**
         * Создаёт базовый контекст с набором модулей.
         */
        fun of(vararg list: Module) =
            base().apply { loadedModules += list }
    }
}