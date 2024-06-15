package ru.DmN.test.cpp.pht

import ru.DmN.test.cpp.TestModule
import kotlin.test.assertEquals

class Math : TestModule("test/pht/cpp/math") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "66\n")
        assertEquals(test(1), "12\n")
        assertEquals(test(2), "4\n")
        assertEquals(test(3), "3\n")
        assertEquals(test(4), "1\n")
        assertEquals(test(5), "-15\n")
    }
}