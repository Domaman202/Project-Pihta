package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class LazySymbol : TestModule("test/pht/jvm/lazy-symbol") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), test(1))
    }

    override fun testUnparse() = Unit
    override fun testPrint() = Unit
}