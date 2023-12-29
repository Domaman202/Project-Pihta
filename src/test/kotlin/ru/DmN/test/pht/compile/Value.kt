package ru.DmN.test.pht.compile

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class Value {
    @Test
    fun test() {
        Module("test/pht/compile/value").run {
            compile()
            assertEquals(test(0), null)
            assertEquals(test(1), 12)
            assertEquals(test(2), 21.33)
            assertEquals(test(3), 44f)
            assertEquals(test(4), 202L)
            assertEquals(test(5), "Текст")
            assertEquals(test(6), Any::class.java)
            assertEquals(test(7), List::class.java)
        }
    }
}