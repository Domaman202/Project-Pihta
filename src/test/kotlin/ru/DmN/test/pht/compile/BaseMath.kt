package ru.DmN.test.pht.compile

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class BaseMath {
    @Test
    fun test() {
        Module("test/pht/compile/base-math").run {
            compile()
            for (i in 0..1) {
                assertEquals(test(0 + i * 8), 33)
                assertEquals(test(1 + i * 8), 21)
                assertEquals(test(2 + i * 8), 0)
                assertEquals(test(3 + i * 8), 12)
                assertEquals(test(4 + i * 8), 4)
                assertEquals(test(5 + i * 8), 1)
                assertEquals(test(6 + i * 8), 1)
                assertEquals(test(7 + i * 8), 1024)
            }
        }
    }
}