package ru.DmN.test.jvm.pht.all

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class InnerCls : TestModule("test/pht/all/inner-cls") {
    override fun compileTest() {
        compile()
        assertEquals(test(0)!!.javaClass.name, "TestA")
    }
}