package ru.DmN.test.jvm.pht

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class LambdaA : TestModule("test/pht/jvm/lambda-a") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Арен брат, с днюхой!")
        assertEquals(test(1), 33)
        assertEquals(test(2), 33)
        assertEquals(test(3), 66)
    }
}