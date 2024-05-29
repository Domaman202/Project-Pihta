package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Skip: TestModule("test/pht/jvm/skip") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), 321)
        assertEquals(test(1), 777)
    }
}