package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class ArrB: TestModule("test/pht/jvm/arr-b") {
    override fun compileTest() {
        compile()
        assertEquals((test(0) as IntArray).contentToString(), "[12, 21, 33]")
        assertEquals((test(1) as FloatArray).contentToString(), "[202.0, 203.0, 213.0]")
    }
}