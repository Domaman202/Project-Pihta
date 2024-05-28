package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertNotEquals

class RandSymbol: TestModule("test/pht/jvm/rand-symbol") {
    override fun compileTest() {
        compile()
        assertNotEquals(test(0), test(1))
    }

    override fun testUnparse() = Unit
    override fun testPrint() = Unit
}