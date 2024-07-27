package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class InlineB : TestModule("test/pht/jvm/inline-b") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Bar!")
        assertEquals(test(1), 1941)
        assertEquals(test(2), 1945)
    }
}