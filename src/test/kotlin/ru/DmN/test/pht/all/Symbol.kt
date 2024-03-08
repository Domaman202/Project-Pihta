package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class Symbol : TestModule("test/pht/all/symbol") {
    override fun testPrint() = Unit
    override fun testUnparse() = Unit

    override fun TestModule.compileTest() {
        compile()
        assertNotEquals(test(0), test(1))
        assertEquals(test(2), test(3))
    }
}