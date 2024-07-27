package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class ToEFnB : TestModule("test/pht/jvm/to-efn-b") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Hello, Stepan!")
    }
}