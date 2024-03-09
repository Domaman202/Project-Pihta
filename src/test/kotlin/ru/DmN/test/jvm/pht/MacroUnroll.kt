package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class MacroUnroll : TestModule("test/pht/jvm/macro-unroll") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 777)
    }
}