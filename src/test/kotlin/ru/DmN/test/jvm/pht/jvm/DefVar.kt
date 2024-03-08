package ru.DmN.test.jvm.pht.jvm

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class DefVar : TestModule("test/pht/jvm/def-var") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 12)
        assertEquals(test(1), 21L)
        assertEquals(test(2), 0f)
        assertEquals(test(3), 33.44)
    }
}