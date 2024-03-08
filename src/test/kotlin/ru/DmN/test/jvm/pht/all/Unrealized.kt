package ru.DmN.test.jvm.pht.all

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertFails

class Unrealized : TestModule("test/pht/all/unrealized") {
    override fun TestModule.compileTest() {
        compile()
        assertFails {
            test(0)
        }
    }
}