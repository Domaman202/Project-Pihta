package ru.DmN.test.jvm.pht.jvm

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals
import kotlin.test.assertFails

class Catch : TestModule("test/pht/jvm/catch") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 21)
        assertEquals(test(1), 12)
        assertFails { test(2) }
        assertEquals(test(3), "java.lang.RuntimeException")
    }
}