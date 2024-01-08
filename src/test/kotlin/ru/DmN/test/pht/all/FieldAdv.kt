package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class FieldAdv : Module("test/pht/all/field-adv") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), 333)
        assertEquals(test(0), "Слава России!")
        assertEquals(test(0), "Боже, Путина Храни!")
    }
}