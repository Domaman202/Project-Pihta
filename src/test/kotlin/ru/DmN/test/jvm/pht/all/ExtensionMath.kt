package ru.DmN.test.jvm.pht.all

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class ExtensionMath : TestModule("test/pht/all/extension-math") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), "Слава России")
        assertEquals(test(1), "ЛолКек")
        assertEquals(test(2), "mul")
        assertEquals(test(3), "div")
        assertEquals(test(4), "rem")
    }
}