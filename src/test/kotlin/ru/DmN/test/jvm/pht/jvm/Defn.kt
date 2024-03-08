package ru.DmN.test.jvm.pht.jvm

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Defn : TestModule("test/pht/jvm/defn") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 6)
    }
}