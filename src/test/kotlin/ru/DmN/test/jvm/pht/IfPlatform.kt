package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class IfPlatform : TestModule("test/pht/jvm/if-platform") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Кто не работает - тот ест!")
    }
}