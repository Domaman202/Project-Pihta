package ru.DmN.test.jvm.pht.all

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Cycle : TestModule("test/pht/all/cycle") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 45)
    }
}