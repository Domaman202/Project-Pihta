package ru.DmN.test.cpp.pht

import ru.DmN.test.cpp.TestModule
import kotlin.test.assertEquals

class SymbolInt : TestModule("test/pht/cpp/symbol-int") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "33\n")
    }
}