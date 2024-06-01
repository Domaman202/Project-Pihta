package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class RefFuncD : TestModule("test/pht/jvm/ref-func-d") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Auto Foo!")
        assertEquals(test(1), "Auto Instanced Foo!")
    }
}