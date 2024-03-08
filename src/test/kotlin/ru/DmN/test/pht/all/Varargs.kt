package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertEquals

class Varargs : TestModule("test/pht/all/varargs") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), "Слава России!")
    }
}