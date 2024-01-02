package ru.DmN.test.siberia.all

import ru.DmN.test.Module
import kotlin.test.Test

class ModuleUses {
    @Test
    fun test() {
        Module("test/siberia/all/module-uses").run {
            compile()
        }
    }
}