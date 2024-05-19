package ru.DmN.test.jvm.siberia

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Export : TestModule("test/siberia/jvm/export") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "test/siberia/jvm/export")
        assertEquals(test(1), "test/siberia/jvm/export/helper")
        assertEquals(test(2), "test/siberia/jvm/export/helper")
        assertEquals(test(3), "test/siberia/jvm/export")
        assertEquals(test(4), "test/siberia/jvm/export")
    }
}