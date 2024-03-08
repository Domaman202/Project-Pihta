package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertEquals

class Field : TestModule("test/pht/all/field") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), 213)
    }
}