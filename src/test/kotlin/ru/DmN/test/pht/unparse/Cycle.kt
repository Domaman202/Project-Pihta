package ru.DmN.test.pht.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class Cycle {
    @Test
    fun test() {
        Module("test/pht/unparse/cycle").run {
            unparse()
            unparseCheck()
        }
    }
}