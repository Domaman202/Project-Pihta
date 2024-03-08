package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertEquals

class DefField : TestModule("test/pht/all/def-field") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), 202)
    }
}