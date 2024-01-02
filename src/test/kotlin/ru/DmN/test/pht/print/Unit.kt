package ru.DmN.test.pht.print

import ru.DmN.test.Module
import kotlin.test.Test

class Unit {
    @Test
    fun test() {
        Module("test/pht/print/unit").run {
            print()
            printCheck()
        }
    }
}