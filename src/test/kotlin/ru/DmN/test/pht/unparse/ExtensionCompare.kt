package ru.DmN.test.pht.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class ExtensionCompare {
    @Test
    fun test() {
        Module("test/pht/unparse/extension-compare").run {
            unparse()
            unparseCheck()
        }
    }
}