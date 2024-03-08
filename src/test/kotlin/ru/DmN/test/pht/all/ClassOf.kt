package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertEquals

class ClassOf : TestModule("test/pht/all/class-of") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), Any::class.java)
        assertEquals(test(1), "java.lang.Object")
    }
}