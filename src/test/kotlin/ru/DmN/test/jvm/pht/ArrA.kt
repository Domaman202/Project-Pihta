package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class ArrA : TestModule("test/pht/jvm/arr-a") {
    override fun compileTest() {
        compile()
        assertEquals((test(0) as IntArray).contentToString(), "[0, 0, 0, 0]")
        assertEquals((test(1) as IntArray).contentToString(), "[12, 21, 0, 0]")
        assertEquals(test(2), 33)
    }
}