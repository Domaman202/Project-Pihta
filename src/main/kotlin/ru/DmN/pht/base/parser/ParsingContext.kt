package ru.DmN.pht.base.parser

import ru.DmN.pht.base.Base
import ru.DmN.pht.base.utils.Module
import java.util.*

class ParsingContext (
    val loadedModules: MutableList<Module> = ArrayList(),
    val macros: Stack<UUID> = Stack()) {
    companion object {
        fun base() =
            ParsingContext(mutableListOf(Base))

        fun of(vararg list: Module) =
            base().apply { loadedModules += list }
    }
}