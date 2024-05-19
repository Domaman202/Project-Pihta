package ru.DmN.test.cpp.pht

import ru.DmN.test.cpp.TestModule
import kotlin.test.assertEquals

class Value : TestModule("test/pht/cpp/value") {
    override fun compileTest() {
        compile()
        for (i in 0..7 step 7) {
            assertEquals(test(0 + i), "nullptr\n")
            assertEquals(test(1 + i), "12\n")
            assertEquals(test(2 + i), "21.33\n")
            assertEquals(test(3 + i), "44\n")
            assertEquals(test(4 + i), "202\n")
            assertEquals(test(5 + i), "@\n")
            assertEquals(test(6 + i), "Текст\n")
        }
    }
}