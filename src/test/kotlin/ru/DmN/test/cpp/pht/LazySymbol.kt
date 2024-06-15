package ru.DmN.test.cpp.pht

import ru.DmN.test.cpp.TestModule
import kotlin.test.assertEquals

class LazySymbol : TestModule("test/pht/cpp/lazy-symbol") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), test(1))
    }

    override fun testUnparse() = Unit
    override fun testPrint() = Unit
}