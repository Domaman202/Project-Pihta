package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Body: TestModule("test/pht/jvm/body") {
    override fun compileTest() {
        compile()
        assertEquals(test(), "12\n21\n")
    }
}