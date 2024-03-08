package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertEquals

class Cycle : TestModule("test/pht/all/cycle") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), 45)
    }
}