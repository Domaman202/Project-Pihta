package ru.DmN.test.siberia.print

import ru.DmN.test.Module
import kotlin.test.Test

class Export {
    @Test
    fun test() {
        Module("test/siberia/print/export").run {
            log()
            logCheck()
        }
    }
}