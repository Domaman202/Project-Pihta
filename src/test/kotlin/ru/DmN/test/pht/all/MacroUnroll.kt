package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals

class MacroUnroll : Module("test/pht/all/macro-unroll") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), 777)
    }
}