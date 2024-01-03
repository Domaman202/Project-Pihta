package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals

class TestFn : Module("test/pht/all/test-fn") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), "Текст из Сибири.")
        assertEquals(test(1), "Текст из Кавказа.")
        assertEquals(test(2), "Текст из Донбасса.")
    }
}