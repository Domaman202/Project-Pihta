package ru.DmN.test.pht.compile

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class TestFn {
    @Test
    fun test() {
        Module("test/pht/compile/test-fn").run {
            compile()
            assertEquals(test(0), "Текст из Сибири.")
            assertEquals(test(1), "Текст из Кавказа.")
            assertEquals(test(2), "Текст из Донбасса.")
        }
    }
}