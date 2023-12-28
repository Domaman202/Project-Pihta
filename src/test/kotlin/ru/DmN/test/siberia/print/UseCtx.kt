package ru.DmN.test.siberia.print

import ru.DmN.test.Module
import kotlin.test.Test

class UseCtx {
    @Test
    fun test() {
        Module("test/siberia/print/use-ctx").run {
            log()
            logCheck()
        }
    }
}