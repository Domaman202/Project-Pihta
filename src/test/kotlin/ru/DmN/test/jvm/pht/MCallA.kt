package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class MCallA : TestModule("test/pht/jvm/mcall-a") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), Unit)
        assertEquals(test(1), Unit)
        assertEquals(test(2), Unit)
        assertEquals(test(3), Unit)
    }
}