package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Cond : TestModule("test/pht/jvm/cond") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 1)
        assertEquals(test(1), 2)
        assertEquals(test(2), 3)
    }
}