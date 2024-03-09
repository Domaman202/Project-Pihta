package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class DefField : TestModule("test/pht/jvm/def-field") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 202)
    }
}