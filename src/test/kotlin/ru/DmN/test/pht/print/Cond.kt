package ru.DmN.test.pht.print

import ru.DmN.test.Module
import kotlin.test.Test

class Cond {
    @Test
    fun test() {
        Module("test/pht/print/cond").run {
            log()
            logCheck()
        }
    }
}