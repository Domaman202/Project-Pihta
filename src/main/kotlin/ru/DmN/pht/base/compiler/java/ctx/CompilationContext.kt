package ru.DmN.pht.base.compiler.java.ctx

import ru.DmN.pht.base.Base
import ru.DmN.pht.base.compiler.java.utils.CompileStage
import ru.DmN.pht.base.utils.Module
import ru.DmN.pht.std.base.compiler.java.utils.SubList
import ru.DmN.pht.std.base.compiler.java.utils.SubMap
import java.util.concurrent.atomic.AtomicReference

class CompilationContext(
    var stage: AtomicReference<CompileStage>,
    val loadedModules: MutableList<Module> = ArrayList(),
    val contexts: MutableMap<String, Any> = HashMap()) {
    fun subCtx() =
        CompilationContext(stage, SubList(loadedModules), SubMap(contexts))
    fun with(name: String, ctx: Any) =
        CompilationContext(stage, loadedModules, contexts.toMutableMap().apply { this[name] = ctx })

    companion object {
        fun base() =
            CompilationContext(AtomicReference(CompileStage.UNKNOWN), mutableListOf(Base))
    }
}