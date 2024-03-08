package ru.DmN.test.jvm.pht.jvm

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class ClassOf : TestModule("test/pht/jvm/class-of") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), Any::class.java)
        assertEquals(test(1), "java.lang.Object")
    }
}