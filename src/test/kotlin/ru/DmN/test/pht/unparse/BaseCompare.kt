package ru.DmN.test.pht.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class BaseCompare {
    @Test
    fun test() {
        Module("test/pht/unparse/base-compare").run {
            unparse()
            unparseCheck()
        }
    }
}