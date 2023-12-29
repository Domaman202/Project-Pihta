package ru.DmN.test.pht.compile

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class Cond {
    @Test
    fun test() {
        Module("test/pht/compile/cond").run {
            compile()
            assertEquals(test(0), "МРБ")
        }
    }
}