package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertEquals

class BaseCompare : TestModule("test/pht/all/base-compare") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), false)
        assertEquals(test(1), true)
        assertEquals(test(2), false)
        assertEquals(test(3), true)
        assertEquals(test(4), false)
        assertEquals(test(5), true)
    }
}