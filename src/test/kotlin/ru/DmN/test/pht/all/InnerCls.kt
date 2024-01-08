package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals

class InnerCls : Module("test/pht/all/inner-cls") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0)!!.javaClass.name, "TestA")
    }
}