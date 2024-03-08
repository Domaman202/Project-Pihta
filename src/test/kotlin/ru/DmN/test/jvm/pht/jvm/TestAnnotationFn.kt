package ru.DmN.test.jvm.pht.jvm

import ru.DmN.test.jvm.TestModule
import kotlin.test.assertEquals

class TestAnnotationFn : TestModule("test/pht/jvm/test-fn") {
    override fun compileTest() {
        compile()
        assertEquals(test(0), "Текст из Сибири.")
        assertEquals(test(1), "Текст из Кавказа.")
        assertEquals(test(2), "Текст из Донбасса.")
    }
}