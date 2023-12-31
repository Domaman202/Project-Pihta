package ru.DmN.test.pht.all

import ru.DmN.test.Module
import kotlin.test.assertEquals

class AliasType : Module("test/pht/all/alias-type") {
    override fun Module.compileTest() {
        compile()
        assertEquals(test(0), Any::class.java)
        assertEquals(test(1), String::class.java)
    }
}