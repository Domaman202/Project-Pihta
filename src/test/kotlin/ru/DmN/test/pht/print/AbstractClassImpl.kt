package ru.DmN.test.pht.print

import ru.DmN.test.Module
import kotlin.test.Test

class AbstractClassImpl {
    @Test
    fun test() {
        Module("test/pht/print/abstract-class-impl").run {
            log()
            logCheck()
        }
    }
}