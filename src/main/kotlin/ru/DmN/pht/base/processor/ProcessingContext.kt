package ru.DmN.pht.base.processor

import ru.DmN.pht.base.Base
import ru.DmN.pht.base.utils.Module

class ProcessingContext(val loadedModules: MutableList<Module> = ArrayList()) {
    companion object {
        fun base() =
            ProcessingContext(mutableListOf(Base))
    }
}