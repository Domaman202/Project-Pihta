package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Compare : TestModule("test/pht/jvm/compare") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), true)
        assertEquals(test(1), true)
        assertEquals(test(2), true)
        assertEquals(test(3), true)
        assertEquals(test(4), true)
        assertEquals(test(5), true)
    }
}