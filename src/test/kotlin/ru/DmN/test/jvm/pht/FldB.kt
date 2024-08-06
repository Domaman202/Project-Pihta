package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class FldB : TestModule("test/pht/jvm/fld-b") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 33)
    }
}