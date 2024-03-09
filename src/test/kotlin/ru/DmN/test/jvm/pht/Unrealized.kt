package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertFails

class Unrealized : TestModule("test/pht/jvm/unrealized") {
    override fun compileTest() {
        compile()
        assertFails {
            test(0)
        }
    }
}