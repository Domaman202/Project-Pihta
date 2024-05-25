package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Math : TestModule("test/pht/jvm/math") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 66)
        assertEquals(test(1), 12)
        assertEquals(test(2), 4)
        assertEquals(test(3), 3)
        assertEquals(test(4), 1)
        assertEquals(test(5), -15)
    }
}