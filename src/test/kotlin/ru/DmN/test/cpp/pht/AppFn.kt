package ru.DmN.test.cpp.pht

import ru.DmN.test.cpp.TestModule
import kotlin.test.assertEquals

class AppFn: TestModule("test/pht/cpp/app-fn") {
    override fun compileTest() {
        compile()
        assertEquals(test(), "Слава России!\n")
    }
}