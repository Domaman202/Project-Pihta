package ru.DmN.test.pht.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class Value {
    @Test
    fun test() {
        Module("test/pht/unparse/value").run {
            unparse()
            unparseCheck()
        }
    }
}