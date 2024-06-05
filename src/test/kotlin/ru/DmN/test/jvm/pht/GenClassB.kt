package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class GenClassB : TestModule("test/pht/jvm/gen-class-b") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "java.lang.Object")
        assertEquals(test(1), "java.lang.String")
    }
}