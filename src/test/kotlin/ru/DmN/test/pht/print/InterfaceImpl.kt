package ru.DmN.test.pht.print

import ru.DmN.test.Module
import kotlin.test.Test

class InterfaceImpl {
    @Test
    fun test() {
        Module("test/pht/print/interface-impl").run {
            print()
            printCheck()
        }
    }
}