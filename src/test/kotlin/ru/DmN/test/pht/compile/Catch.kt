package ru.DmN.test.pht.compile

import ru.DmN.test.Module
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class Catch {
    @Test
    fun test() {
        Module("test/pht/compile/catch").run {
            compile()
            assertEquals(test(0), 21)
            assertEquals(test(1), 12)
            assertFails { test(2) }
        }
    }
}