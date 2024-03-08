package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertEquals

class Unroll : TestModule("test/pht/all/unroll") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), 5)
        assertEquals(test(1), 8)
    }
}