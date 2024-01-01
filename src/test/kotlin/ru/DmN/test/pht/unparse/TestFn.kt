package ru.DmN.test.pht.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class TestFn {
    @Test
    fun test() {
        Module("test/pht/unparse/test-fn").run {
            unparse()
            unparseCheck()
        }
    }
}