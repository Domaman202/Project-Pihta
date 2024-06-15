package ru.DmN.test.cpp.pht

import ru.DmN.test.cpp.TestModule
import kotlin.test.assertEquals

class If : TestModule("test/pht/cpp/if") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Да\n")
        assertEquals(test(1), "Нет\n")
    }
}