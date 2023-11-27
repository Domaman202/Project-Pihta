package ru.DmN.siberia.compiler.java.utils

import ru.DmN.siberia.Base
import ru.DmN.pht.base.processor.utils.Platform
import ru.DmN.pht.base.processor.utils.with
import ru.DmN.pht.base.processor.utils.withJCV
import ru.DmN.pht.base.utils.IContextCollection
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.base.utils.getJavaClassVersion
import ru.DmN.pht.std.compiler.java.utils.SubList
import ru.DmN.pht.std.compiler.java.utils.SubMap
import java.util.concurrent.atomic.AtomicReference

class CompilationContext(
    val stage: AtomicReference<CompilingStage>,
    val loadedModules: MutableList<Module> = ArrayList(),
    override val contexts: MutableMap<String, Any?> = HashMap()) : IContextCollection<CompilationContext> {
    fun subCtx() =
        CompilationContext(stage, SubList(loadedModules), SubMap(contexts))
    override fun with(name: String, ctx: Any?): CompilationContext =
        CompilationContext(stage, loadedModules, contexts.toMutableMap().apply { this[name] = ctx })

    companion object {
        fun base() =
            CompilationContext(AtomicReference(CompilingStage.UNKNOWN), mutableListOf(ru.DmN.siberia.Base)).with(Platform.JAVA).withJCV(getJavaClassVersion())
    }
}