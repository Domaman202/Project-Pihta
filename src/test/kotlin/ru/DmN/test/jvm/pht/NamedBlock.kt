package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class NamedBlock : TestModule("test/pht/jvm/named-block") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 6)
        assertEquals(test(1), 5)
    }
}