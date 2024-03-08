package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertEquals

class MCallAdv : TestModule("test/pht/all/mcall-adv") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), "Foo!")
        assertEquals(test(1), "Foo static!")
        assertEquals(test(2), "Foo outer!")
        assertEquals(test(3), "Foo outer outer!")
        assertEquals(test(4), 33)
    }
}