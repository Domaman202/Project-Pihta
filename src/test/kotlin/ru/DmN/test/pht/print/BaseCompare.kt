package ru.DmN.test.pht.print

import ru.DmN.test.Module
import kotlin.test.Test

class BaseCompare {
    @Test
    fun test() {
        Module("test/pht/print/base-compare").run {
            print()
            printCheck()
        }
    }
}