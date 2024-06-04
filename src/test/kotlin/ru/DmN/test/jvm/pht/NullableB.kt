package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertFails

class NullableB : TestModule("test/pht/jvm/nullable-b") {
    override fun compileTest() {
        compile()
        assertFails { test(0) }
    }
}