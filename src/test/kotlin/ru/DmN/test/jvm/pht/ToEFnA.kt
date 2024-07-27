package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class ToEFnA : TestModule("test/pht/jvm/to-efn-a") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Hello, Petya!")
    }
}