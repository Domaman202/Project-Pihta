package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals

class Varargs : Module("test/pht/all/varargs") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), "Слава России!")
    }
}