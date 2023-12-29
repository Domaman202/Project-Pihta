package ru.DmN.test.pht.print

import ru.DmN.test.Module
import kotlin.test.Test

class TestFn {
    @Test
    fun test() {
        Module("test/pht/print/test-fn").run {
            log()
            logCheck()
        }
    }
}