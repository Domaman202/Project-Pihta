package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class MCallC : TestModule("test/pht/jvm/mcall-c") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), Unit)
        assertEquals(test(1), Unit)
        assertEquals(test(2), Unit)
        assertEquals(test(3), Unit)
    }
}