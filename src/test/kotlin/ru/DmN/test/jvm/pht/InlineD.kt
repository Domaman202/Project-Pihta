package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class InlineD : TestModule("test/pht/jvm/inline-d") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 33)
        assertEquals(test(1), 33)
        assertEquals(test(2), 33.21)
        assertEquals(test(3), 33.33)
    }
}