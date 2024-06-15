package ru.DmN.test.cpp.pht

import ru.DmN.test.cpp.TestModule
import kotlin.test.assertEquals

class LogicBits : TestModule("test/pht/cpp/logic-bits") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "0\n")
        assertEquals(test(1), "1\n")
        assertEquals(test(2), "1\n")
        assertEquals(test(3), "0\n")
        assertEquals(test(4), "2\n")
        assertEquals(test(5), "3\n")
        assertEquals(test(6), "1\n")
        assertEquals(test(7), "0\n")
        assertEquals(test(8), "1024\n")
        assertEquals(test(9), "1\n")
    }
}