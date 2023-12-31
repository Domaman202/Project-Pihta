package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals

class ExtensionCompare : Module("test/pht/all/extension-compare") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), "great")
        assertEquals(test(1), "great-or-eq")
        assertEquals(test(2), "less")
        assertEquals(test(3), "less-or-eq")
        assertEquals(test(4), "eq")
        assertEquals(test(5), "not-eq")
    }
}