package ru.DmN.test.pht.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class Interface {
    @Test
    fun test() {
        Module("test/pht/unparse/interface").run {
            unparse()
            unparseCheck()
        }
    }
}