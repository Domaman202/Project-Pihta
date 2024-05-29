package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertNotNull

class CtorA : TestModule("test/pht/jvm/ctor-a") {
    override fun compileTest() {
        compile()
        assertNotNull(test(0))
    }
}