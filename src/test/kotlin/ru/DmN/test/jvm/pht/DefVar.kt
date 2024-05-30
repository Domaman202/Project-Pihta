package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class DefVar : TestModule("test/pht/jvm/def-var") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 12)
        assertEquals(test(1), 21f)
        assertEquals(test(2), 33.0)
        assertEquals(test(3), 44.0)
        assertEquals(test(4), 202)
        assertEquals(test(5), 203)
    }
}