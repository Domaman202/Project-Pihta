package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals

class IncDec : Module("test/pht/all/inc-dec") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), 1)
        assertEquals(test(1), 1)
        assertEquals(test(2), 0)
        assertEquals(test(3), -1)
        assertEquals(test(4), -1)
        assertEquals(test(5), 0)
    }
}