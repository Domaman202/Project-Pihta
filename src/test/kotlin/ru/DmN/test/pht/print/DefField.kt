package ru.DmN.test.pht.print

import ru.DmN.test.Module
import kotlin.test.Test

class DefField {
    @Test
    fun test() {
        Module("test/pht/print/def-field").run {
            print()
            printCheck()
        }
    }
}