package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class FldA : TestModule("test/pht/jvm/fld-a") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 12)
        assertEquals(test(1), 21)
    }
}