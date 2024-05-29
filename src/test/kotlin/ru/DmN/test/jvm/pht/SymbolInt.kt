package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class SymbolInt : TestModule("test/pht/jvm/symbol-int") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 33)
    }
}