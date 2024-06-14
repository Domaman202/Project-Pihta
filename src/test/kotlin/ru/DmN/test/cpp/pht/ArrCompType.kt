package ru.DmN.test.cpp.pht

import ru.DmN.test.cpp.TestModule
import kotlin.test.assertEquals

class ArrCompType : TestModule("test/pht/cpp/arr-comp-type") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "^[dmn.pht.object\n")
        assertEquals(test(1), "^dmn.pht.object\n")
    }
}