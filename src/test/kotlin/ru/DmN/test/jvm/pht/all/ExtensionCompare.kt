package ru.DmN.test.jvm.pht.all

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class ExtensionCompare : TestModule("test/pht/all/extension-compare") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), "great")
        assertEquals(test(1), "great-or-eq")
        assertEquals(test(2), "less")
        assertEquals(test(3), "less-or-eq")
        assertEquals(test(4), "eq")
        assertEquals(test(5), "not-eq")
    }
}