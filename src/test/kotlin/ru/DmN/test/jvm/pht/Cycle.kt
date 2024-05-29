package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Cycle : TestModule("test/pht/jvm/cycle") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 10)
    }
}