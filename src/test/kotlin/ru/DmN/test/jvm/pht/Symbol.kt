package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class Symbol : TestModule("test/pht/jvm/symbol") {
    override fun testPrint() = Unit
    override fun testUnparse() = Unit

    override fun compileTest() {
        compile()
        assertNotEquals(test(0), test(1))
        assertEquals(test(2), test(3))
    }
}