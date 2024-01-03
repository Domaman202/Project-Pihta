package ru.DmN.test.siberia.all

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class Export : Module("test/siberia/all/export") {
    @Test
    override fun testUnparse() {
        unparse()
        unparseCheck()
        (object : Module("${module.name}/unparse") { }).run {
            compile()
            assertEquals(test(0), "test/siberia/all/export")
            assertEquals(test(1), "test/siberia/all/export/helper")
            assertEquals(test(2), "test/siberia/all/export/unparse")
        }
    }

    @Test
    override fun testCompile() {
        compile()
        assertEquals(test(0), "test/siberia/all/export")
        assertEquals(test(1), "test/siberia/all/export/helper")
        assertEquals(test(2), "test/siberia/all/export")
    }
}