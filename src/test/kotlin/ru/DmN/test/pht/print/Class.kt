package ru.DmN.test.pht.print

import ru.DmN.test.Module
import kotlin.test.Test

class Class {
    @Test
    fun test() {
        Module("test/pht/print/class").run {
            print()
            printCheck()
        }
    }
}