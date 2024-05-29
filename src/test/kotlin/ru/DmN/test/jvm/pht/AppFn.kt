package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class AppFn : TestModule("test/pht/jvm/app-fn") {
    override fun compileTest() {
        compile()
        assertEquals(test(), "Слава России!\n")
    }
}