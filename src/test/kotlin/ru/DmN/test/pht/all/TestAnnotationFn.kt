package ru.DmN.test.pht.all

import ru.DmN.test.TestModule
import kotlin.test.assertEquals

class TestAnnotationFn : TestModule("test/pht/all/test-fn") {
    override fun TestModule.compileTest() {
        compile()
        assertEquals(test(0), "Текст из Сибири.")
        assertEquals(test(1), "Текст из Кавказа.")
        assertEquals(test(2), "Текст из Донбасса.")
    }
}