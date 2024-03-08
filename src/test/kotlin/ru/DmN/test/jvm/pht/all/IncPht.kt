package ru.DmN.test.jvm.pht.all

import ru.DmN.test.jvm.TestModule
import kotlin.test.Test
import kotlin.test.assertEquals

class IncPht : TestModule("test/pht/all/inc-pht") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), "Внешний вставленный код!")
    }

    @Test
    override fun testUnparse() {
        unparse()
        unparseCheck()
        (object : TestModule("${dir}/test/unparse/processed") { }).compileTest()
    }
}