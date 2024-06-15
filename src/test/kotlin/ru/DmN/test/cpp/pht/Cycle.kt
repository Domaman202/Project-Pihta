package ru.DmN.test.cpp.pht

import ru.DmN.test.cpp.TestModule
import kotlin.test.assertEquals

class Cycle : TestModule("test/pht/cpp/cycle") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "10\n")
    }
}