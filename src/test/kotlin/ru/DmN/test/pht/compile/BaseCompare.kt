package ru.DmN.test.pht.compile

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class BaseCompare {
    @Test
    fun test() {
        Module("test/pht/compile/base-compare").run {
            compile()
            assertEquals(test(0), false)
            assertEquals(test(1), true)
            assertEquals(test(2), false)
            assertEquals(test(3), true)
            assertEquals(test(4), false)
            assertEquals(test(5), true)
        }
    }
}