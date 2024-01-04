package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals

class Defn : Module("test/pht/all/defn") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), 6)
    }
}