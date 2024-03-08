package ru.DmN.test.jvm.pht.all

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class ClassOf : TestModule("test/pht/all/class-of") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), Any::class.java)
        assertEquals(test(1), "java.lang.Object")
    }
}