package ru.DmN.test.pht.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class Class {
    @Test
    fun test() {
        Module("test/pht/unparse/class").run {
            unparse()
            unparseCheck()
        }
    }
}