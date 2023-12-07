package ru.DmN.siberia.test

import kotlin.test.Test
import kotlin.test.assertEquals

class Tests {
    @Test
    fun testExport() {
        Module("test/base/export").run {
            compile()
            assertEquals(test(0), "test/base/export")
            assertEquals(test(1), "test/base/export/helper")
            assertEquals(test(2), "test/base/export")
        }
    }

    @Test
    fun testBaseModuleUses() {
        Module("test/base/module-uses").run {
            compile()
        }
    }


    @Test
    fun testBaseUseCtx() {
        Module("test/base/use-ctx").run {
            compile()
        }
    }
}