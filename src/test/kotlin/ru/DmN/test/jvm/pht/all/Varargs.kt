package ru.DmN.test.jvm.pht.all

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Varargs : TestModule("test/pht/all/varargs") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Слава России!")
    }
}