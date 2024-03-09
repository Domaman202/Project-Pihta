package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Value : TestModule("test/pht/jvm/value") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), null)
        assertEquals(test(1), 12)
        assertEquals(test(2), 21.33)
        assertEquals(test(3), 44f)
        assertEquals(test(4), 202L)
        assertEquals(test(5), "Текст")
        assertEquals(test(6), Any::class.java)
        assertEquals(test(7), List::class.java)
    }
}