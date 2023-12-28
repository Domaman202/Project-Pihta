package ru.DmN.test.siberia.compile

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class Export {
    @Test
    fun test() {
        Module("test/siberia/compile/export").run {
            compile()
            assertEquals(test(0), "test/siberia/compile/export")
            assertEquals(test(1), "test/siberia/compile/export/helper")
            assertEquals(test(2), "test/siberia/compile/export")
        }
    }
}