package ru.DmN.test.jvm.pht.all

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class DefVar : TestModule("test/pht/all/def-var") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), 12)
        assertEquals(test(1), 21L)
        assertEquals(test(2), 0f)
        assertEquals(test(3), 33.44)
    }
}