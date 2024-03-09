package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Generics : TestModule("test/pht/jvm/generics") {
    override fun compileTest() {
        compile()
        for (i in 0..7) {
            assertEquals(test(i), String::class.java)
        }
    }
}