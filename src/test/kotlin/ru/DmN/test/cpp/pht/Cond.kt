package ru.DmN.test.cpp.pht

import ru.DmN.test.cpp.TestModule
import kotlin.test.assertEquals

class Cond : TestModule("test/pht/cpp/cond") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "1\n")
        assertEquals(test(1), "2\n")
        assertEquals(test(2), "3\n")
    }
}