package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertEquals

class Generics : TestModule("test/pht/all/generics") {
    override fun TestModule.compileTest() {
        compile()
        for (i in 0..7) {
            assertEquals(test(i), String::class.java)
        }
    }
}