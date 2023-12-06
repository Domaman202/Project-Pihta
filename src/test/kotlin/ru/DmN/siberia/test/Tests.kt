package ru.DmN.siberia.test

import kotlin.test.Test

class Tests {
    @Test
    fun testExport() {
        Module("test/base/export").run {
            compile()
            println(test(0))
            println(test(1))
            println(test(2))
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