package ru.DmN.test.pht.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class ExtensionMath {
    @Test
    fun test() {
        Module("test/pht/unparse/extension-math").run {
            unparse()
            unparseCheck()
        }
    }
}