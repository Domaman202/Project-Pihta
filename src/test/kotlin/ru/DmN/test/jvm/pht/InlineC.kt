package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class InlineC : TestModule("test/pht/jvm/inline-c") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "ZV")
    }
}