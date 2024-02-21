package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals
import kotlin.test.assertFails

class Unrealized : Module("test/pht/all/unrealized") {
    override fun Module.compileTest() {
        compile()
        assertFails {
            test(0)
        }
    }
}