package ru.DmN.test.pht.compile

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class DefField {
    @Test
    fun test() {
        Module("test/pht/compile/def-field").run {
            compile()
            assertEquals(test(0), 202)
        }
    }
}