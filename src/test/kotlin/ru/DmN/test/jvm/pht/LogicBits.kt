package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class LogicBits: TestModule("test/pht/jvm/logic-bits") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), false)
        assertEquals(test(1), true)
        assertEquals(test(2), true)
        assertEquals(test(3), false)
        assertEquals(test(4), 2)
        assertEquals(test(5), 3)
        assertEquals(test(6), 1)
        assertEquals(test(7), 1)
        assertEquals(test(8), 1024)
        assertEquals(test(9), 1)
    }
}