package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class BaseCompare : Module("test/pht/all/base-compare") {
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
        assertEquals(test(0), false)
        assertEquals(test(1), true)
        assertEquals(test(2), false)
        assertEquals(test(3), true)
        assertEquals(test(4), false)
        assertEquals(test(5), true)
    }
}