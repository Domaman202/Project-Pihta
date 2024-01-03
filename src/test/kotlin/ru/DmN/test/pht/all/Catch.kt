package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class Catch : Module("test/pht/all/catch") {
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
        assertEquals(test(0), 21)
        assertEquals(test(1), 12)
        assertFails { test(2) }
    }
}