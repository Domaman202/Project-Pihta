package ru.DmN.test.cpp.pht

import ru.DmN.test.cpp.TestModule
import kotlin.test.assertEquals

class DefVar : TestModule("test/pht/cpp/def-var") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "12\n")
        assertEquals(test(1), "21\n")
        assertEquals(test(2), "33\n")
        assertEquals(test(3), "44\n")
        assertEquals(test(4), "202\n")
        assertEquals(test(5), "203\n")
    }
}