package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class GenEfnA : TestModule("test/pht/jvm/gen-efn-a") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "java.lang.Class")
    }
}