package ru.DmN.test.jvm.pht.all

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Defn : TestModule("test/pht/all/defn") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), 6)
    }
}