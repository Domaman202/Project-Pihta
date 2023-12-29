package ru.DmN.test.pht.compile

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class Unroll {
    @Test
    fun test() {
        Module("test/pht/compile/unroll").run {
            compile()
            assertEquals(test(0), 5)
            assertEquals(test(1), 8)
        }
    }
}