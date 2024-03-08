package ru.DmN.test.jvm.pht.all

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class RFn : TestModule("test/pht/all/rfn") {
    override fun compileTest() {
        compile()
        test(0)
        test(1)
        test(2)
        test(3)
        assertEquals(test(4), "Foo Static Supply!")
        assertEquals(test(5), "Foo Supply!")
        assertEquals(test(6), "Foo Static Function!")
        assertEquals(test(7), "Foo Function!")
        assertEquals(test(8), "Foo AutoType!")
        assertEquals(test(9), "Foo All Auto!")
    }
}