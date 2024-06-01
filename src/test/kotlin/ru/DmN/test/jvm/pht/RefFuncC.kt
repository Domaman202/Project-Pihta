package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class RefFuncC : TestModule("test/pht/jvm/ref-func-c") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Auto-Provider Foo!")
        assertEquals(test(1), "Auto-Provider Instanced Foo!")
    }
}