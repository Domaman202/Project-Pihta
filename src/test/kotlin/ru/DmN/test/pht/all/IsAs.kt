package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertEquals

class IsAs : TestModule("test/pht/all/is-as") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), true)
        assertEquals(test(1), false)
        assertEquals(test(2), false)
        assertEquals(test(3), 12L)
        assertEquals(test(4), 21f)
        assertEquals(test(5), 21L as Long?)
        assertEquals(test(6), true)
        assertEquals(test(7), true)
        assertEquals(test(8), "Слава России!")
    }
}