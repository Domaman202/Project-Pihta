package ru.DmN.test.pht.print

import ru.DmN.test.Module
import kotlin.test.Test

class Value {
    @Test
    fun test() {
        Module("test/pht/print/value").run {
            print()
            printCheck()
        }
    }
}