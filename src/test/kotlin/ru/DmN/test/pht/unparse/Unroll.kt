package ru.DmN.test.pht.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class Unroll {
    @Test
    fun test() {
        Module("test/pht/unparse/unroll").run {
            unparse()
            unparseCheck()
        }
    }
}