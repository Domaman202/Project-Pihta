package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertNotNull

class Symbol: TestModule("test/pht/jvm/symbol") {
    override fun compileTest() {
        compile()
        assertNotNull(test(0))
    }
}