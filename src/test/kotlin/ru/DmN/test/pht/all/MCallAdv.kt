package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals

class MCallAdv : Module("test/pht/all/mcall-adv") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), "Foo Static!")
        assertEquals(test(1), "Foo!")
    }
}