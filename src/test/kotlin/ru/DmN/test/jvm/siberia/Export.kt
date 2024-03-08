package ru.DmN.test.jvm.siberia

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertTrue

class Export : TestModule("test/siberia/jvm/export") {
    override fun compileTest() {
        compile()
        assertTrue((test(0) as String).contains("export"))
        assertTrue((test(1) as String).contains("helper"))
        assertTrue((test(2) as String).contains("export"))
    }
}