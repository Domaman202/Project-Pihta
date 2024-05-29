package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Is : TestModule("test/pht/jvm/is") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), true)
        assertEquals(test(1), false)
        assertEquals(test(2), false)
        assertEquals(test(3), false)
        assertEquals(test(4), true)
        assertEquals(test(5), true)
        assertEquals(test(6), false)
    }
}