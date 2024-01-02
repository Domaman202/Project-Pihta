package ru.DmN.test.pht.print

import ru.DmN.test.Module
import kotlin.test.Test

class Catch {
    @Test
    fun test() {
        Module("test/pht/print/catch").run {
            print()
            printCheck()
        }
    }
}