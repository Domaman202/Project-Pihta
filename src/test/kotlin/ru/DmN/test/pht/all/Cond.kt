package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class Cond : Module("test/pht/all/cond") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), "МРБ")
    }
}