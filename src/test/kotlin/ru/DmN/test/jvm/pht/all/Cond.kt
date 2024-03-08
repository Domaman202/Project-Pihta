package ru.DmN.test.jvm.pht.all

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Cond : TestModule("test/pht/all/cond") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "МРБ")
    }
}