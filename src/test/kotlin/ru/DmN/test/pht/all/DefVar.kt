package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class DefVar : Module("test/pht/all/def-var") {
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
        assertEquals(test(0), 12)
        assertEquals(test(1), 21L)
        assertEquals(test(2), 0f)
        assertEquals(test(3), 33.44)
    }
}