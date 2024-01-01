package ru.DmN.test.pht.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class Unit {
    @Test
    fun test() {
        Module("test/pht/unparse/unit").run {
            unparse()
            unparseCheck()
        }
    }
}