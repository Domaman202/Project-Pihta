package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class EFn : TestModule("test/pht/jvm/efn") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Hello, Vasya!")
    }
}