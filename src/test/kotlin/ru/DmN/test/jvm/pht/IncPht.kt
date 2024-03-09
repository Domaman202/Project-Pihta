package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.Test
import kotlin.test.assertEquals

class IncPht : TestModule("test/pht/jvm/inc-pht") {
    override fun compileTest() {
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