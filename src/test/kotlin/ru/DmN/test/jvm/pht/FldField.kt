package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class FldField : TestModule("test/pht/jvm/fld-field") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 12)
        assertEquals(test(1), 21)
    }
}