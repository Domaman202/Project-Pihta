package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule

class ListOf: TestModule("test/pht/jvm/list-of") {
    override fun compileTest() {
        compile()
        assert(test(0) == listOf("Слава", "Великой", "России"))
    }
}