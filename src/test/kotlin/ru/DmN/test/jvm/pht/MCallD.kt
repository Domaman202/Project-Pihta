package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class MCallD : TestModule("test/pht/jvm/mcall-d") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 10)
        assertEquals(test(1), 25)
        assertEquals(test(2), 30)
        assertEquals(test(3), 50)
    }
}