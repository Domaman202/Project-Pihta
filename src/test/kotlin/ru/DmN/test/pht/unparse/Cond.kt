package ru.DmN.test.pht.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class Cond {
    @Test
    fun test() {
        Module("test/pht/unparse/cond").run {
            unparse()
            unparseCheck()
        }
    }
}