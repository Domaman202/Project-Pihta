package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class ArrayBase : TestModule("test/pht/jvm/array-base") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "[9 8 7 6 5 4 3 2 1 0]")
    }
}