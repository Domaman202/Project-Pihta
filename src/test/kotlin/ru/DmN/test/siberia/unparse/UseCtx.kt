package ru.DmN.test.siberia.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class UseCtx {
    @Test
    fun test() {
        Module("test/siberia/unparse/use-ctx").run {
            unparse()
            unparseCheck()
        }
    }
}