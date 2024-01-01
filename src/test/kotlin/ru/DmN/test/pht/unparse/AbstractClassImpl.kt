package ru.DmN.test.pht.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class AbstractClassImpl {
    @Test
    fun test() {
        Module("test/pht/unparse/abstract-class-impl").run {
            unparse()
            unparseCheck()
        }
    }
}