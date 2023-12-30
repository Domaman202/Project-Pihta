package ru.DmN.test.pht.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class BaseMath {
    @Test
    fun test() {
        Module("test/pht/unparse/base-math").run {
            unparse()
            unparseCheck()
        }
    }
}