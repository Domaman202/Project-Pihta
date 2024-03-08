package ru.DmN.test.jvm.pht.jvm

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Unroll : TestModule("test/pht/jvm/unroll") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 5)
        assertEquals(test(1), 8)
    }
}