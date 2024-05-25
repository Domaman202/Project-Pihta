package ru.DmN.test.cpp.pht

import ru.DmN.test.cpp.TestModule
import kotlin.test.assertEquals

class App: TestModule("test/pht/cpp/app") {
    override fun compileTest() {
        compile()
        assertEquals(test(), "Слава России!\n")
    }
}