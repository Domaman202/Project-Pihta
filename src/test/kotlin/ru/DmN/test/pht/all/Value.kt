package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class Value : Module("test/pht/all/value") {
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
        assertEquals(test(0), null)
        assertEquals(test(1), 12)
        assertEquals(test(2), 21.33)
        assertEquals(test(3), 44f)
        assertEquals(test(4), 202L)
        assertEquals(test(5), "Текст")
        assertEquals(test(6), Any::class.java)
        assertEquals(test(7), List::class.java)
    }
}