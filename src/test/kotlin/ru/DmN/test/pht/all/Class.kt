package ru.DmN.test.pht.all

import ru.DmN.siberia.utils.Klass
import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Class : Module("test/pht/all/class") {
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
        (test(0) as Klass).let {
            assertEquals(it.name, "Test")
            assertEquals(it.superclass, Any::class.java)
            assertTrue(it.interfaces.isEmpty())
        }
    }
}