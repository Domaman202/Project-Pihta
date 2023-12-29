package ru.DmN.test.pht.print

import ru.DmN.test.Module
import kotlin.test.Test

class Unroll {
    @Test
    fun test() {
        Module("test/pht/print/unroll").run {
            log()
            logCheck()
        }
    }
}