package ru.DmN.siberia.processor.utils

import ru.DmN.siberia.utils.IContextCollection
import ru.DmN.siberia.utils.Module
import ru.DmN.pht.std.compiler.java.utils.SubList
import ru.DmN.pht.std.compiler.java.utils.SubMap
import java.util.concurrent.atomic.AtomicReference

/**
 * Контекст обработки.
 */
class ProcessingContext(
    /**
     * Текущая стадия обработки.
     */
    val stage: AtomicReference<ProcessingStage>,
    /**
     * Загруженные модули.
     */
    val loadedModules: MutableList<Module> = ArrayList(),
    /**
     * Контексты
     */
    override val contexts: MutableMap<String, Any?> = HashMap()
) : IContextCollection<ProcessingContext> {
    /**
     * Создаёт под-контекст.
     */
    fun subCtx() =
        ProcessingContext(stage, SubList(loadedModules), SubMap(contexts))

    /**
     * Создаёт под-контекст с общими модульными зависимостями.
     * Добавляет новый элемент контекста.
     *
     * @param name Имя нового элемента контекста.
     * @param ctx Новый элемент контекста.
     */
    override fun with(name: String, ctx: Any?): ProcessingContext =
        ProcessingContext(stage, loadedModules, contexts.toMutableMap().apply { this[name] = ctx })
    companion object {
        /**
         * Создаёт базовый контекст.
         */
        fun base() =
            ProcessingContext(AtomicReference(ProcessingStage.UNKNOWN), mutableListOf(ru.DmN.siberia.Siberia))
    }
}