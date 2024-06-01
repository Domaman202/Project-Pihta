package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class MCallB : TestModule("test/pht/jvm/mcall-b") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Foo!")
        assertEquals(test(1), "Instanced Foo!")
        assertEquals(test(2), "Auto Foo!")
        assertEquals(test(3), "Auto Instanced Foo!")
    }
}