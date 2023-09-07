package ru.DmN.pht.base.parser

import ru.DmN.pht.base.Base
import ru.DmN.pht.base.utils.Module
import java.util.Stack

class ParsingContext (
    val modules: MutableList<Module> = ArrayList(),
    val loadedModules: MutableList<Module> = ArrayList(),
    val macros: Stack<String> = Stack()) {
    companion object {
        fun base() =
            ParsingContext(mutableListOf(Base), mutableListOf(Base))

        fun of(list: List<Module>) =
            base().apply { modules += list; loadedModules += list }
    }
}