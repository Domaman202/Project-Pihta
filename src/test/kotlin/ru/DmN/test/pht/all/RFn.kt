package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals

class RFn : Module("test/pht/all/rfn") {
    override fun Module.compileTest() {
        compile()
        test(0)
        test(1)
        test(2)
        test(3)
        assertEquals(test(4), "Foo Static Supply!")
        assertEquals(test(5), "Foo Supply!")
        assertEquals(test(6), "Foo Static Function!")
        assertEquals(test(7), "Foo Function!")
    }
}