package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals

class Unroll : Module("test/pht/all/unroll") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), 5)
        assertEquals(test(1), 8)
    }
}