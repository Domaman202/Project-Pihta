package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class CtorB : TestModule("test/pht/jvm/ctor-b") {
    override fun compileTest() {
        compile()
        assertEquals(test(), "202\n203\n")
    }
}