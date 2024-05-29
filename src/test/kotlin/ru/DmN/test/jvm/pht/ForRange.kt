package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class ForRange: TestModule("test/pht/jvm/for-range") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 55)
    }
}