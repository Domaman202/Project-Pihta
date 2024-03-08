package ru.DmN.test.jvm.pht.all

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class MacroUnroll : TestModule("test/pht/all/macro-unroll") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), 777)
    }
}