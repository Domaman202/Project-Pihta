package ru.DmN.test.pht.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class InterfaceImpl {
    @Test
    fun test() {
        Module("test/pht/unparse/interface-impl").run {
            unparse()
            unparseCheck()
        }
    }
}