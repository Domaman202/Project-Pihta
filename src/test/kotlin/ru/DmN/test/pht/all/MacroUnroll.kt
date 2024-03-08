package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertEquals

class MacroUnroll : TestModule("test/pht/all/macro-unroll") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), 777)
    }
}