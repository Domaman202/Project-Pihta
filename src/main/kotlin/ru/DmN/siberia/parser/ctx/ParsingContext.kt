package ru.DmN.siberia.parser.ctx

import ru.DmN.siberia.parser.utils.parsersPool
import ru.DmN.siberia.utils.IContextCollection
import ru.DmN.siberia.utils.Module
import ru.DmN.pht.std.compiler.java.utils.SubList
import ru.DmN.pht.std.compiler.java.utils.SubMap
import java.util.*
import kotlin.collections.HashMap

class ParsingContext (
    val loadedModules: MutableList<Module> = ArrayList(),
    override val contexts: MutableMap<String, Any?> = HashMap()
) : IContextCollection<ParsingContext> {
    fun subCtx() =
        ParsingContext(SubList(loadedModules), SubMap(contexts))

    override fun with(name: String, ctx: Any?): ParsingContext =
        ParsingContext(SubList(loadedModules), SubMap(contexts).apply { this[name] = ctx })

    companion object {
        fun base() =
            ParsingContext(mutableListOf(ru.DmN.siberia.Siberia)).apply { this.parsersPool = Stack() }

        fun of(vararg list: Module) =
            base().apply { loadedModules += list }
    }
}