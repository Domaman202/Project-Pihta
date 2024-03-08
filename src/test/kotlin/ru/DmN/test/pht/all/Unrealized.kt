package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertFails

class Unrealized : TestModule("test/pht/all/unrealized") {
    override fun TestModule.compileTest() {
        compile()
        assertFails {
            test(0)
        }
    }
}