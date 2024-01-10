package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals

class Fn : Module("test/pht/all/fn") {
    override fun Module.compileTest() {
        compile()
        test(0)
        test(1)
        test(2)
        assertEquals(test(3), "Foo D!")
        assertEquals(test(4), "Foo E!")
        assertEquals(test(5), 33)
    }
}