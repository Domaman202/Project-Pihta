package ru.DmN.test.cpp.siberia

import ru.DmN.test.cpp.TestModule
import kotlin.test.assertTrue

class Export : TestModule("test/siberia/cpp/export") {
    override fun compileTest() {
        compile()
        assertTrue(test(0).contains("export"))
        assertTrue(test(1).contains("helper"))
        assertTrue(test(2).contains("export"))
    }
}