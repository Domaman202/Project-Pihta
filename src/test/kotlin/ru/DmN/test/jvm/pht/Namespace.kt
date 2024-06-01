package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class Namespace : TestModule("test/pht/jvm/namespace") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Слава Владимиру Путину!")
    }
}