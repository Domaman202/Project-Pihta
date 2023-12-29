package ru.DmN.test.pht.print

import ru.DmN.test.Module
import kotlin.test.Test

class Cycle {
    @Test
    fun test() {
        Module("test/pht/print/cycle").run {
            log()
            logCheck()
        }
    }
}