package ru.DmN.test.pht.print

import ru.DmN.test.Module
import kotlin.test.Test

class BaseMath {
    @Test
    fun test() {
        Module("test/pht/print/base-math").run {
            log()
            logCheck()
        }
    }
}