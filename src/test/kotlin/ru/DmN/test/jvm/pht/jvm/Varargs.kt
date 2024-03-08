package ru.DmN.test.jvm.pht.jvm

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Varargs : TestModule("test/pht/jvm/varargs") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Слава России!")
    }
}