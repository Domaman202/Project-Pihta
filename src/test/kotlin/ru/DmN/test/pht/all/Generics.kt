package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals

class Generics : Module("test/pht/all/generics") {
    override fun Module.compileTest() {
        compile()
        for (i in 0..7) {
            assertEquals(test(i), String::class.java)
        }
    }
}