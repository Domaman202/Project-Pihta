package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Inline : TestModule("test/pht/jvm/inline") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Foo!")
        assertEquals(test(1), 33)
        assertEquals(test(2), "Сало!")
        assertEquals(test(3), "Сало!")
        assertEquals(test(4), "Сало Сало!")
        assertEquals(test(5), "Слава Великой России!")
    }
}