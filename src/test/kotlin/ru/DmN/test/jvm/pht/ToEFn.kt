package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class ToEFn : TestModule("test/pht/jvm/to-efn") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Hello, Petya!")
    }
}