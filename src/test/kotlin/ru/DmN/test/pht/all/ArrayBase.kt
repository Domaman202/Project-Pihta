package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals

class ArrayBase : Module("test/pht/all/array-base") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), "[9 8 7 6 5 4 3 2 1 0]")
    }
}