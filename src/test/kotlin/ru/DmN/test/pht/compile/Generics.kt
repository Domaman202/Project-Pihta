package ru.DmN.test.pht.compile

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class Generics {
    @Test
    fun test() {
        Module("test/pht/compile/generics").run {
            compile()
            for (i in 0..7) {
                assertEquals(test(i), String::class.java)
            }
        }
    }
}