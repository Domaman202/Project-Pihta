package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class RefFuncA : TestModule("test/pht/jvm/ref-func-a") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Foo!")
        assertEquals(test(1), "Instanced Foo!")
    }
}