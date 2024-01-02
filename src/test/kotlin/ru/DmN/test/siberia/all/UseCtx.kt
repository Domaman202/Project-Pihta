package ru.DmN.test.siberia.all

import ru.DmN.test.Module
import kotlin.test.Test

class UseCtx {
    @Test
    fun test() {
        Module("test/siberia/all/use-ctx").run {
            compile()
        }
    }
}