package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals
import kotlin.test.assertFails

class As : TestModule("test/pht/jvm/as") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 33)
        assertEquals(test(1), 33f)
        assertEquals(test(2), 44)
        assertEquals(test(3), 44)
        assertEquals(test(4), "Слава России!")
        assertFails { test(5) }
    }
}