package ru.DmN.test.cpp.siberia

import ru.DmN.test.cpp.TestModule
import kotlin.test.assertEquals

class Export : TestModule("test/siberia/cpp/export") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "test/siberia/cpp/export\n")
        assertEquals(test(1), "test/siberia/cpp/export/helper\n")
        assertEquals(test(2), "test/siberia/cpp/export/helper\n")
        assertEquals(test(3), "test/siberia/cpp/export\n")
        assertEquals(test(4), "test/siberia/cpp/export\n")
    }
}