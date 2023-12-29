package ru.DmN.test.pht.compile

import ru.DmN.test.Module
import kotlin.test.Test

class Unit {
    @Test
    fun test() {
        Module("test/pht/compile/unit").run {
            compile()
        }
    }
}