package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals

class DefField : Module("test/pht/all/def-field") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), 202)
    }
}