package ru.DmN.test.jvm.pht.all

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Generics : TestModule("test/pht/all/generics") {
    override fun compileTest() {
        compile()
        for (i in 0..7) {
            assertEquals(test(i), String::class.java)
        }
    }
}