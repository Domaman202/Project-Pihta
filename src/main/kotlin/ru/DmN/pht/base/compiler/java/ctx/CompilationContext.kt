package ru.DmN.pht.base.compiler.java.ctx

import ru.DmN.pht.base.compiler.java.utils.CompileStage
import ru.DmN.pht.base.utils.Module

class CompilationContext(var stage: CompileStage, val modules: MutableList<Module> = ArrayList(), val contexts: MutableMap<String, Any> = HashMap()) {
    fun with(modules: MutableList<Module>) =
        CompilationContext(stage, (this.modules + modules).toMutableList(), contexts)
    fun with(contexts: MutableMap<String, Any>) =
        CompilationContext(stage, modules, (this.contexts + contexts).toMutableMap())
    fun with(name: String, ctx: Any) =
        CompilationContext(stage, modules, contexts.toMutableMap().apply { this[name] = ctx })
}