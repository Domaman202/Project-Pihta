package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class FieldAdv : TestModule("test/pht/jvm/field-adv") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 333)
        assertEquals(test(1), "Слава России!")
        assertEquals(test(2), "Боже, Путина Храни!")
    }
}