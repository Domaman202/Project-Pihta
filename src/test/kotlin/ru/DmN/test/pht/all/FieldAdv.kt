package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertEquals

class FieldAdv : TestModule("test/pht/all/field-adv") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), 333)
        assertEquals(test(1), "Слава России!")
        assertEquals(test(2), "Боже, Путина Храни!")
    }
}