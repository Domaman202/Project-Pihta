package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class ExtFn : TestModule("test/pht/jvm/ext-fn") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Hello, Vasya!")
    }
}