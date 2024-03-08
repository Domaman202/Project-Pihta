package ru.DmN.test.jvm.pht.all

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Fn : TestModule("test/pht/all/fn") {
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