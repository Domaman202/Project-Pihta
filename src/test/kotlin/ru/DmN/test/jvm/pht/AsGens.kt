package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class AsGens : TestModule("test/pht/jvm/as-gens") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "java.lang.Class")
        assertEquals(test(1), "java.lang.String")
    }
}