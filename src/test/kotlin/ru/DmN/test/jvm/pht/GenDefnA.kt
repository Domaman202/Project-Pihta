package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class GenDefnA : TestModule("test/pht/jvm/gen-defn-a") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "java.lang.Object")
        assertEquals(test(1), "java.lang.String")
        assertEquals(test(2), "java.lang.Class")
    }
}