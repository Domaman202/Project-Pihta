package ru.DmN.test.jvm.pht.jvm

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class IncDec : TestModule("test/pht/jvm/inc-dec") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 1)
        assertEquals(test(1), 1)
        assertEquals(test(2), 0)
        assertEquals(test(3), -1)
        assertEquals(test(4), -1)
        assertEquals(test(5), 0)
    }
}