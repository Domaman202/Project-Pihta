package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Value : TestModule("test/pht/jvm/value") {
    override fun compileTest() {
        compile()
        for (i in 0..9 step 9) {
            assertEquals(test(0 + i), null)
            assertEquals(test(1 + i), 12)
            assertEquals(test(2 + i), 21.33)
            assertEquals(test(3 + i), 44f)
            assertEquals(test(4 + i), 202L)
            assertEquals(test(5 + i), '@')
            assertEquals(test(6 + i), "Текст")
            assertEquals(test(7 + i), Any::class.java)
            assertEquals(test(8 + i), List::class.java)
        }
    }
}