package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class ExtensionMath : Module("test/pht/all/extension-math") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), "Слава России")
        assertEquals(test(1), "ЛолКек")
        assertEquals(test(2), "mul")
        assertEquals(test(3), "div")
        assertEquals(test(4), "rem")
    }
}