package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class IncPht : Module("test/pht/all/inc-pht") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), "Внешний вставленный код!")
    }

    @Test
    override fun testUnparse() {
        unparse()
        unparseCheck()
        (object : Module("${dir}/unparse/processed") { }).compileTest()
    }
}