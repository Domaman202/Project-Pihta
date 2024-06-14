package ru.DmN.test.cpp.pht

import ru.DmN.test.cpp.TestModule
import kotlin.test.assertEquals

class As : TestModule("test/pht/cpp/as") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "33\n")
        assertEquals(test(1), "33\n")
        assertEquals(test(2), "44\n")
        assertEquals(test(3), "44\n")
        assertEquals(test(4), "Слава России!\n")
        assert(test(5).startsWith("PN3dmn3pht6objectE"))
    }
}