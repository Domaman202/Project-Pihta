package ru.DmN.pht.std.test

import kotlin.test.Test
import kotlin.test.assertEquals

class Tests {
    @Test
    fun testBaseModuleUses() {
        compileModule("test/base/module-uses")
    }


    @Test
    fun testBaseUseCtx() {
        compileModule("test/base/use-ctx")
    }

    @Test
    fun testTestFn() {
        compileModule("test/test-fn")
        assertEquals(runModule("test/test-fn", 0), "Текст из Сибири.")
        assertEquals(runModule("test/test-fn", 1), "Текст из Кавказа.")
        assertEquals(runModule("test/test-fn", 2), "Текст из Донбасса.")
    }

    @Test
    fun testUnit() {
        compileModule("test/unit")
    }
}