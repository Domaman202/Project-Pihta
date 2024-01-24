package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals

class Inline : Module("test/pht/all/inline") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), "Foo!")
    }
}