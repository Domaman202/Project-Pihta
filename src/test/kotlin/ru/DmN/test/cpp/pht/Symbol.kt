package ru.DmN.test.cpp.pht

import ru.DmN.test.cpp.TestModule

class Symbol : TestModule("test/pht/cpp/symbol") {
    override fun compileTest() {
        compile()
        assert(test(0).startsWith("PN3dmn3pht6objectE"))
    }
}