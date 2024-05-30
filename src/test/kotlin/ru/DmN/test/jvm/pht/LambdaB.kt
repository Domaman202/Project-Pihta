package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class LambdaB : TestModule("test/pht/jvm/lambda-b") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Glory To Russia!")
        assertEquals(test(1), 132)
        assertEquals(test(2), 132)
        assertEquals(test(3), 176)
    }
}