package ru.DmN.test.pht.print

import ru.DmN.test.Module
import kotlin.test.Test

class DefVar {
    @Test
    fun test() {
        Module("test/pht/print/def-var").run {
            log()
            logCheck()
        }
    }
}