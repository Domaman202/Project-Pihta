package ru.DmN.pht.base.parser

import ru.DmN.pht.base.Base
import ru.DmN.pht.base.utils.Module
import java.util.*

class ParsingContext (
    val loadedModules: MutableList<Module> = ArrayList(),
    val macros: Stack<String> = Stack()) {
    companion object {
        fun base() =
            ParsingContext(mutableListOf(Base))

        fun of(list: List<Module>) =
            base().apply { loadedModules += list }
    }
}