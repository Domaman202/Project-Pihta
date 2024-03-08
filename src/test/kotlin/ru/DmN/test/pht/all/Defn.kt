package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertEquals

class Defn : TestModule("test/pht/all/defn") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), 6)
    }
}