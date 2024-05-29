package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class TrCall : TestModule("test/pht/jvm/trcall") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 24)
    }
}