package ru.DmN.test.siberia.compile

import ru.DmN.test.Module
import kotlin.test.Test

class ModuleUses {
    @Test
    fun test() {
        Module("test/siberia/compile/module-uses").run {
            compile()
        }
    }
}