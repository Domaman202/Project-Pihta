package ru.DmN.test.pht.compile

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals

class ExtensionMath {
    @Test
    fun test() {
        Module("test/pht/compile/extension-math").run {
            compile()
            assertEquals(test(0), "Слава России")
            assertEquals(test(1), "ЛолКек")
            assertEquals(test(2), "mul")
            assertEquals(test(3), "div")
            assertEquals(test(4), "rem")
        }
    }
}