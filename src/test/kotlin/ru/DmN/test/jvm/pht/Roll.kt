package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Roll : TestModule("test/pht/jvm/roll") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 2)
        assertEquals(test(1), 32)
    }
}