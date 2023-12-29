package ru.DmN.test.pht.print

import ru.DmN.test.Module
import kotlin.test.Test

class ExtensionMath {
    @Test
    fun test() {
        Module("test/pht/print/extension-math").run {
            log()
            logCheck()
        }
    }
}