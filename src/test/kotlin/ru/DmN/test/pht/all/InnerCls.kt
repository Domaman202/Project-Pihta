package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertEquals

class InnerCls : TestModule("test/pht/all/inner-cls") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0)!!.javaClass.name, "TestA")
    }
}