package ru.DmN.test.cpp.pht

import ru.DmN.test.cpp.TestModule
import kotlin.test.assertEquals

class AliasType : TestModule("test/pht/cpp/alias-type") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "^dmn.pht.object\n")
    }
}