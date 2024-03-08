package ru.DmN.test.jvm.pht.jvm

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Fn : TestModule("test/pht/jvm/fn") {
    override fun compileTest() {
        compile()
        test(0)
        test(1)
        test(2)
        assertEquals(test(3), "Foo D!")
        assertEquals(test(4), "Foo E!")
        assertEquals(test(5), 33)
    }
}