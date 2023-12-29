package ru.DmN.test.pht.compile

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class ExtensionCompare {
    @Test
    fun test() {
        Module("test/pht/compile/extension-compare").run {
            compile()
            assertEquals(test(0), "great")
            assertEquals(test(1), "great-or-eq")
            assertEquals(test(2), "less")
            assertEquals(test(3), "less-or-eq")
            assertEquals(test(4), "eq")
            assertEquals(test(5), "not-eq")
        }
    }
}