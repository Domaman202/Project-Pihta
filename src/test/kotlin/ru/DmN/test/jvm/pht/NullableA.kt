package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertFails

class NullableA : TestModule("test/pht/jvm/nullable-a") {
    override fun compileTest() {
        assertFails { compile() }
    }

    override fun testPrint() = Unit
    override fun testUnparse() = Unit
}