package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.Enum
import kotlin.test.assertEquals

class Enum : Module("test/pht/all/enum") {
    override fun Module.compileTest() {
        compile()
        assertEquals((test(0) as Enum<*>).ordinal, 0)
        assertEquals((test(0) as Enum<*>).name, "RED")
        assertEquals((test(1) as Enum<*>).ordinal, 1)
        assertEquals((test(1) as Enum<*>).name, "GREEN")
        assertEquals((test(2) as Enum<*>).ordinal, 2)
        assertEquals((test(2) as Enum<*>).name, "BLUE")
    }
}