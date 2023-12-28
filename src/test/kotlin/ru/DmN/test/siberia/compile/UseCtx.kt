package ru.DmN.test.siberia.compile

import ru.DmN.test.Module
import kotlin.test.Test

class UseCtx {
    @Test
    fun test() {
        Module("test/siberia/compile/use-ctx").run {
            compile()
        }
    }
}