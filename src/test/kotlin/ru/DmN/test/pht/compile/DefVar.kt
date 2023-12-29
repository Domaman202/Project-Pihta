package ru.DmN.test.pht.compile

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class DefVar {
    @Test
    fun test() {
        Module("test/pht/compile/def-var").run {
            compile()
            assertEquals(test(0), 12)
            assertEquals(test(1), 21L)
            assertEquals(test(2), 0f)
            assertEquals(test(3), 33.44)
        }
    }
}