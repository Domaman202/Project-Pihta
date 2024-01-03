package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class Field : Module("test/pht/all/field") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), 213)
    }
}