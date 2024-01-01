package ru.DmN.test.pht.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class DefField {
    @Test
    fun test() {
        Module("test/pht/unparse/def-field").run {
            unparse()
            unparseCheck()
        }
    }
}