package ru.DmN.test.siberia.all

import ru.DmN.test.TestModule
import kotlin.test.assertTrue

class Export : TestModule("test/siberia/all/export") {
    override fun TestModule.compileTest() {
        compile()
        assertTrue((test(0) as String).contains("export"))
        assertTrue((test(1) as String).contains("helper"))
        assertTrue((test(2) as String).contains("export"))
    }
}