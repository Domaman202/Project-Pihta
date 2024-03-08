package ru.DmN.test.jvm.pht.jvm

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class IsAs : TestModule("test/pht/jvm/is-as") {
    override fun compileTest() {
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