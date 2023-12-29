package ru.DmN.test.pht.print

import ru.DmN.test.Module
import kotlin.test.Test

class Generics {
    @Test
    fun test() {
        Module("test/pht/print/generics").run {
            log()
            logCheck()
        }
    }
}