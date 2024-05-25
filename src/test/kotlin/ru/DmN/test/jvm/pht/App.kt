package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class App: TestModule("test/pht/jvm/app") {
    override fun compileTest() {
        compile()
        assertEquals(test(), "Слава России!\n")
    }
}