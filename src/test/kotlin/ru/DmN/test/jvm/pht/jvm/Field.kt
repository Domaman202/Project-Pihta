package ru.DmN.test.jvm.pht.jvm

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Field : TestModule("test/pht/jvm/field") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 213)
    }
}