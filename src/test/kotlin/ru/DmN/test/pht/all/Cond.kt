package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertEquals

class Cond : TestModule("test/pht/all/cond") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), "МРБ")
    }
}