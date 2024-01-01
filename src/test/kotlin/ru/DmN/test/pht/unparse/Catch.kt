package ru.DmN.test.pht.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class Catch {
    @Test
    fun test() {
        Module("test/pht/unparse/catch").run {
            unparse()
            unparseCheck()
        }
    }
}