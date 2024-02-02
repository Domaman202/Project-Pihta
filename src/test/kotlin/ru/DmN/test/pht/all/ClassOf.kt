package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals

class ClassOf : Module("test/pht/all/class-of") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), Any::class.java)
        assertEquals(test(1), "java.lang.Object")
    }
}