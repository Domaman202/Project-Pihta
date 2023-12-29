package ru.DmN.test.siberia.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class ModuleUse {
    @Test
    fun test() {
        Module("test/siberia/unparse/module-uses").run {
            unparse()
            unparseCheck()
        }
    }
}