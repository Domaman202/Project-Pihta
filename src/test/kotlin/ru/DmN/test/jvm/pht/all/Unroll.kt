package ru.DmN.test.jvm.pht.all

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Unroll : TestModule("test/pht/all/unroll") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 5)
        assertEquals(test(1), 8)
    }
}