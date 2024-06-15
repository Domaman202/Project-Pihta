package ru.DmN.test.cpp.pht

import ru.DmN.test.cpp.TestModule
import kotlin.test.assertNotEquals

class RandSymbol : TestModule("test/pht/cpp/rand-symbol") {
    override fun compileTest() {
        compile()
        assertNotEquals(test(0), test(1))
    }

    override fun testUnparse() = Unit
    override fun testPrint() = Unit
}