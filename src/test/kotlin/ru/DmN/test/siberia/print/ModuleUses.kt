package ru.DmN.test.siberia.print

import ru.DmN.test.Module
import kotlin.test.Test

class ModuleUses {
    @Test
    fun test() {
        Module("test/siberia/print/module-uses").run {
            log()
            logCheck()
        }
    }
}