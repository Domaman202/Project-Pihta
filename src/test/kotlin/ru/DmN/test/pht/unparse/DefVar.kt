package ru.DmN.test.pht.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class DefVar {
    @Test
    fun test() {
        Module("test/pht/unparse/def-var").run {
            unparse()
            unparseCheck()
        }
    }
}