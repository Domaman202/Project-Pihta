package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class WithGens : TestModule("test/pht/jvm/with-gens") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "java.lang.String")
        assertEquals(test(1), "java.lang.Class")
    }
}