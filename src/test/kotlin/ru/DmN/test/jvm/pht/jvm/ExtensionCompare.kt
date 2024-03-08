package ru.DmN.test.jvm.pht.jvm

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class ExtensionCompare : TestModule("test/pht/jvm/extension-compare") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "great")
        assertEquals(test(1), "great-or-eq")
        assertEquals(test(2), "less")
        assertEquals(test(3), "less-or-eq")
        assertEquals(test(4), "eq")
        assertEquals(test(5), "not-eq")
    }
}