package ru.DmN.siberia.compiler.ctx

import ru.DmN.siberia.processor.utils.Platform
import ru.DmN.siberia.processor.utils.with
import ru.DmN.siberia.utils.IContextCollection
import ru.DmN.siberia.utils.Module
import ru.DmN.siberia.utils.getJavaClassVersion
import ru.DmN.pht.std.compiler.java.utils.SubList
import ru.DmN.pht.std.compiler.java.utils.SubMap
import ru.DmN.siberia.Siberia
import ru.DmN.siberia.compiler.utils.CompilingStage
import ru.DmN.siberia.compiler.utils.withJCV
import java.util.concurrent.atomic.AtomicReference

/**
 * Контекст компиляции.
 */
class CompilationContext(
    /**
     * Текущая стадия компиляции.
     */
    val stage: AtomicReference<CompilingStage>,
    /**
     * Загруженные модули.
     */
    val loadedModules: MutableList<Module> = ArrayList(),
    /**
     * Контексты
     */
    override val contexts: MutableMap<String, Any?> = HashMap()
) : IContextCollection<CompilationContext> {
    /**
     * Создаёт под-контекст.
     */
    fun subCtx() =
        CompilationContext(stage, SubList(loadedModules), SubMap(contexts))

    /**
     * Создаёт под-контекст с общими модульными зависимостями.
     * Добавляет новый элемент контекста.
     *
     * @param name Имя нового элемента контекста.
     * @param ctx Новый элемент контекста.
     */
    override fun with(name: String, ctx: Any?): CompilationContext =
        CompilationContext(stage, loadedModules, contexts.toMutableMap().apply { this[name] = ctx })

    companion object {
        /**
         * Создаёт базовый контекст.
         */
        fun base(): CompilationContext =
            CompilationContext(AtomicReference(CompilingStage.UNKNOWN), mutableListOf(Siberia)).with(Platform.JAVA).withJCV(getJavaClassVersion())
    }
}