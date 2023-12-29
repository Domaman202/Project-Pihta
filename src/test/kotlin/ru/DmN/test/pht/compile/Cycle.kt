package ru.DmN.test.pht.compile

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class Cycle {
    @Test
    fun test() {
        Module("test/pht/compile/cycle").run {
            compile()
            assertEquals(test(0), 45)
        }
    }
}