package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class GFnC : TestModule("test/pht/jvm/gfn-c") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 15)
        assertEquals(test(1), 12.5)
    }
}