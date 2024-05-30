package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class If : TestModule("test/pht/jvm/if") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Да")
        assertEquals(test(1), "Нет")
    }
}