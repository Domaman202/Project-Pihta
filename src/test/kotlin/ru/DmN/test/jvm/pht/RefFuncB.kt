package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class RefFuncB : TestModule("test/pht/jvm/ref-func-b") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Auto-Type Foo!")
        assertEquals(test(1), "Auto-Type Instanced Foo!")
    }
}