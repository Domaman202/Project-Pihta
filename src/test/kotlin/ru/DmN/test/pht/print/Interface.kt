package ru.DmN.test.pht.print

import ru.DmN.test.Module
import kotlin.test.Test

class Interface {
    @Test
    fun test() {
        Module("test/pht/print/interface").run {
            print()
            printCheck()
        }
    }
}