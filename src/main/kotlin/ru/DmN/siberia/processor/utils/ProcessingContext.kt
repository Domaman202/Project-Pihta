package ru.DmN.siberia.processor.utils

import ru.DmN.siberia.utils.IContextCollection
import ru.DmN.siberia.utils.Module
import ru.DmN.pht.std.compiler.java.utils.SubList
import ru.DmN.pht.std.compiler.java.utils.SubMap
import java.util.concurrent.atomic.AtomicReference

class ProcessingContext(
    val stage: AtomicReference<ProcessingStage>,
    val loadedModules: MutableList<Module> = ArrayList(),
    override val contexts: MutableMap<String, Any?> = HashMap()) : IContextCollection<ProcessingContext> {
    fun subCtx() =
        ProcessingContext(stage, SubList(loadedModules), SubMap(contexts))
    override fun with(name: String, ctx: Any?): ProcessingContext =
        ProcessingContext(stage, loadedModules, contexts.toMutableMap().apply { this[name] = ctx })
    companion object {
        fun base() =
            ProcessingContext(AtomicReference(ProcessingStage.UNKNOWN), mutableListOf(ru.DmN.siberia.Siberia))
    }
}