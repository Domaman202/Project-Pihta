package ru.DmN.test.jvm.pht.all

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Field : TestModule("test/pht/all/field") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 213)
    }
}