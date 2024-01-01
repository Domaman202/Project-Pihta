package ru.DmN.test.pht.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class Field {
    @Test
    fun test() {
        Module("test/pht/unparse/field").run {
            unparse()
            unparseCheck()
        }
    }
}