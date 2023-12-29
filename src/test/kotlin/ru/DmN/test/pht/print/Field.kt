package ru.DmN.test.pht.print

import ru.DmN.test.Module
import kotlin.test.Test

class Field {
    @Test
    fun test() {
        Module("test/pht/print/field").run {
            log()
            logCheck()
        }
    }
}