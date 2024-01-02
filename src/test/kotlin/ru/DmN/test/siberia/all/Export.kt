package ru.DmN.test.siberia.all

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class Export : Module("test/siberia/all/export") {
    @Test
    fun testPrint() {
        print()
        printCheck()
    }

    @Test
    fun testUnparse() {
        unparse()
        unparseCheck()
    }

    @Test
    fun testCompile() {
        compile()
        assertEquals(test(0), "test/siberia/all/export")
        assertEquals(test(1), "test/siberia/all/export/helper")
        assertEquals(test(2), "test/siberia/all/export")
    }
}