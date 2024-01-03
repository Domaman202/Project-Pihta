package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals

class Cycle : Module("test/pht/all/cycle") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), 45)
    }
}