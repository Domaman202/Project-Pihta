package ru.DmN.pht.std.test

import kotlin.test.Test
import kotlin.test.assertEquals

class Tests {
    @Test
    fun testBaseModuleUses() {
        Module("test/base/module-uses").run {
            compileModule()
        }
    }


    @Test
    fun testBaseUseCtx() {
        Module("test/base/use-ctx").run {
            compileModule()
        }
    }

    @Test
    fun testBaseMath() {
        Module("test/pht/base-math").run {
            compileModule()
            assertEquals(runModule(0), 33)
            assertEquals(runModule(1), 21)
            assertEquals(runModule(2), 0)
            assertEquals(runModule(3), 12)
            assertEquals(runModule(4), 4)
            assertEquals(runModule(5), 1)
        }
    }

    @Test
    fun testExtensionMath() {
        Module("test/pht/extension-math").run {
            compileModule()
            assertEquals(runModule(0), "Слава России")
            assertEquals(runModule(1), "ЛолКек")
            assertEquals(runModule(2), "mul")
            assertEquals(runModule(3), "div")
            assertEquals(runModule(4), "rem")
        }
    }

    @Test
    fun testTestFn() {
        Module("test/pht/test-fn").run {
            compileModule()
            assertEquals(runModule(0), "Текст из Сибири.")
            assertEquals(runModule(1), "Текст из Кавказа.")
            assertEquals(runModule(2), "Текст из Донбасса.")
        }
    }

    @Test
    fun testUnit() {
        Module("test/pht/unit").run {
            compileModule()
        }
    }

    @Test
    fun testValue() {
        Module("test/pht/value").run {
            compileModule()
            assertEquals(runModule(0), null)
            assertEquals(runModule(1), 12)
            assertEquals(runModule(2), 21.33)
            assertEquals(runModule(3), 44f)
            assertEquals(runModule(4), 202L)
            assertEquals(runModule(5), "Текст")
            assertEquals(runModule(6), Any::class.java)
        }
    }
}