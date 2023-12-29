package ru.DmN.test.pht.compile

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class Field {
    @Test
    fun test() {
        Module("test/pht/compile/field").run {
            compile()
            assertEquals(test(0), 213)
        }
    }
}