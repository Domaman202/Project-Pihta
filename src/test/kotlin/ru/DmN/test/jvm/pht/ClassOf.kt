package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class ClassOf : TestModule("test/pht/jvm/class-of") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), String::class.java)
    }
}