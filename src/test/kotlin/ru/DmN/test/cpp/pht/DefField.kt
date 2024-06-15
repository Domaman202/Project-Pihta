package ru.DmN.test.cpp.pht

import ru.DmN.test.cpp.TestModule
import kotlin.test.assertEquals

class DefField : TestModule("test/pht/cpp/def-field") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "12\n")
        assertEquals(test(1), "21\n")
    }
}