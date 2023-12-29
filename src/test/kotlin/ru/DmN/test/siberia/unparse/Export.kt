package ru.DmN.test.siberia.unparse

import ru.DmN.test.Module
import kotlin.test.Test

class Export {
    @Test
    fun test() {
        Module("test/siberia/unparse/export").run {
            unparse()
            unparseCheck()
        }
    }
}