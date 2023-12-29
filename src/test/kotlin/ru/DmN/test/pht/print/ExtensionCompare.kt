package ru.DmN.test.pht.print

import ru.DmN.test.Module
import kotlin.test.Test

class ExtensionCompare {
    @Test
    fun test() {
        Module("test/pht/print/extension-compare").run {
            log()
            logCheck()
        }
    }
}