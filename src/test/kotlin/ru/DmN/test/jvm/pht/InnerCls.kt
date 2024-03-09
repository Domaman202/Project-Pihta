package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class InnerCls : TestModule("test/pht/jvm/inner-cls") {
    override fun compileTest() {
        compile()
        assertEquals(test(0)!!.javaClass.name, "TestA")
    }
}